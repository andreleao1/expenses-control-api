package br.com.agls.expensescontrolapi.domain.service.impl;

import br.com.agls.expensescontrolapi.domain.entity.Account;
import br.com.agls.expensescontrolapi.domain.service.AccountService;
import br.com.agls.expensescontrolapi.infra.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountRepository accountRepository;

    @Override
    public void save(Account account) {
        Account accountSaved = this.accountRepository.save(account);
        LOGGER.info("Account {} saved, to user {}", accountSaved.getId(), account.getUserId());
    }

    private Account getAccountByIdAndUserId(String accountId, String userId) {
        LOGGER.info("Fetching account {} to user {}", accountId, userId);
        return this.accountRepository.findByIdAndUserId(UUID.fromString(accountId), userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Account %s not found", accountId)));
    }

    @Override
    public void archive(String accountId, String userId) {
        Account account = getAccountByIdAndUserId(accountId, userId);
        account.setArchived(true);
        this.accountRepository.save(account);
        LOGGER.info("Account {} archived", account.getId());
    }

    @Override
    public void unarchive(String accountId, String userId) {
        Account account = getAccountByIdAndUserId(accountId, userId);
        account.setArchived(false);
        this.accountRepository.save(account);
        LOGGER.info("Account {} unarchived", account.getId());
    }

    @Override
    public void delete(String accountId, String userId) {
        Account account = getAccountByIdAndUserId(accountId, userId);

        if (Boolean.TRUE.equals(account.getArchived()) && account.getBalance().equals(BigDecimal.ZERO.toString())) {
            this.accountRepository.deleteById(account.getId());
            LOGGER.info("Account {} deleted", account.getId());
        } else if(Boolean.FALSE.equals(account.getArchived())){
            LOGGER.error("Error deleting account {} because it isn't archived", account.getId());
            throw new IllegalArgumentException("Error deleting account because it isn't archived");
        } else if(BigDecimal.valueOf(Double.parseDouble(account.getBalance())).compareTo(BigDecimal.ZERO) != 0){
            LOGGER.error("Error deleting account {} because it has balance", account.getId());
            throw new IllegalArgumentException("Error deleting account because it has balance");
        }
    }

    @Override
    public List<Account> getAccountsByUserId(String userId) {
        return this.accountRepository.findByUserId(userId);
    }
}
