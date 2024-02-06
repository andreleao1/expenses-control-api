package br.com.agls.expensescontrolapi.domain.service.impl;

import br.com.agls.expensescontrolapi.domain.entity.Account;
import br.com.agls.expensescontrolapi.domain.service.AccountService;
import br.com.agls.expensescontrolapi.infra.repository.AccountRepository;
import jakarta.persistence.EntityExistsException;
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
        try {
            Account accountSaved = this.accountRepository.save(account);
            LOGGER.info("Account {} saved, to user {}", accountSaved.getId(), account.getUserId());
        } catch (Exception e) {
            LOGGER.error("Error saving account {}", account.getId(), e);
            throw new InternalError("Error saving account", e);
        }
    }

    @Override
    public void archive(String accountId) {
        Account account = getAccountById(accountId);
        account.setArchived(true);
        this.accountRepository.save(account);
        LOGGER.info("Account {} archived", account.getId());
    }

    @Override
    public void unarchive(String accountId) {
        Account account = getAccountById(accountId);
        account.setArchived(false);
        this.accountRepository.save(account);
        LOGGER.info("Account {} unarchived", account.getId());
    }

    public Account getAccountById(String accountId) {
        return this.accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Account %s not found", accountId)));
    }

    @Override
    public void delete(String accountId) {
        Account account = getAccountById(accountId);

        try {
            if (account.getArchived() && account.getBalance().equals(BigDecimal.ZERO.toString())) {
                this.accountRepository.deleteById(account.getId());
                LOGGER.info("Account {} deleted", account.getId());
            } else if(!account.getArchived()){
                LOGGER.error("Error deleting account {} because it isn't archived", account.getId());
                throw new IllegalArgumentException("Error deleting account because it isn't archived");
            } else if(BigDecimal.valueOf(Long.parseLong(account.getBalance())).compareTo(BigDecimal.ZERO) != 0){
                LOGGER.error("Error deleting account {} because it has balance", account.getId());
                throw new IllegalArgumentException("Error deleting account because it has balance");
            }
        }catch (Exception e){
            LOGGER.error("Error deleting account {}", account.getId(), e);
            throw new InternalError("Error deleting account", e);
        }
    }

    @Override
    public List<Account> getAccountsPerUserId(String userId) {
        return this.accountRepository.findByUserId(UUID.fromString(userId));
    }
}
