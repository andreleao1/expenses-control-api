package br.com.agls.expensescontrolapi.domain.service;

import br.com.agls.expensescontrolapi.domain.entity.Account;
import br.com.agls.expensescontrolapi.domain.exceptions.BusinessErroToDeleteEntity;
import br.com.agls.expensescontrolapi.domain.service.impl.AccountServiceImpl;
import br.com.agls.expensescontrolapi.infra.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AccountServiceImplTest {

    private AccountRepository accountRepository;
    private AccountServiceImpl accountService;

    @BeforeEach
    void setup() {
        accountRepository = Mockito.mock(AccountRepository.class);
        accountService = new AccountServiceImpl(accountRepository);
    }

    @Test
    @DisplayName("Save valid account")
    void shouldSaveValidAccountSuccessfully() {
        Account account = new Account();
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        accountService.save(account);

        Mockito.verify(accountRepository, Mockito.times(1)).save(account);
    }

    @Test
    @DisplayName("Archive account")
    void shouldArchiveAccountSuccessfully() {
        Account account = new Account();
        String accountId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        when(accountRepository.findByIdAndUserId(UUID.fromString(accountId), userId)).thenReturn(java.util.Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        accountService.archive(accountId, userId);

        Mockito.verify(accountRepository, Mockito.times(1)).save(account);
    }

    @Test
    @DisplayName("Unarchive account")
    void shouldUnarchiveAccountSuccessfully() {
        Account account = new Account();
        String accountId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        when(accountRepository.findByIdAndUserId(UUID.fromString(accountId), userId)).thenReturn(java.util.Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        accountService.unarchive(accountId, userId);

        Mockito.verify(accountRepository, Mockito.times(1)).save(account);
    }

    @Test
    @DisplayName("Archive account with invalid account id")
    void shouldThrowExceptionWhenArchiveAccountWithInvalidAccountId() {
        String accountId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        when(accountRepository.findByIdAndUserId(UUID.fromString(accountId), userId)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.archive(accountId, userId));
    }

    @Test
    @DisplayName("Unarchived account with invalid account id")
    void shouldThrowExceptionWhenUnarchiveAccountWithInvalidAccountId() {
        String accountId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        when(accountRepository.findByIdAndUserId(UUID.fromString(accountId), userId)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.unarchive(accountId, userId));
    }

    @Test
    @DisplayName("find account by user id")
    void shouldFindAccountByUserId() {
        String userId = UUID.randomUUID().toString();
        when(accountRepository.findByUserId(userId)).thenReturn(java.util.Collections.emptyList());

        accountService.getAccountsByUserId(userId);

        Mockito.verify(accountRepository, Mockito.times(1)).findByUserId(userId);
    }

    @Test
    @DisplayName("Delete account successfully")
    void shouldDeleteAccountSuccessfully() {
        Account account = new Account();
        account.setArchived(true);
        account.setBalance("0");
        String accountId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        when(accountRepository.findByIdAndUserId(UUID.fromString(accountId), userId)).thenReturn(java.util.Optional.of(account));

        accountService.delete(accountId, userId);

        Mockito.verify(accountRepository, Mockito.times(1)).deleteById(account.getId());
    }

    @Test
    @DisplayName("Delete account with invalid account id")
    void shouldThrowExceptionWhenDeleteAccountWithInvalidAccountId() {
        String accountId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        when(accountRepository.findByIdAndUserId(UUID.fromString(accountId), userId)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.delete(accountId, userId));
    }

    @Test
    @DisplayName("Delete account with balance different of zero")
    void shouldThrowExceptionWhenDeleteAccountWithBalanceDifferentOfZero() {
        Account account = new Account();
        account.setArchived(true);
        account.setBalance("10");
        String accountId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        when(accountRepository.findByIdAndUserId(UUID.fromString(accountId), userId)).thenReturn(java.util.Optional.of(account));

        assertThrows(BusinessErroToDeleteEntity.class, () -> accountService.delete(accountId, userId));
    }

    @Test
    @DisplayName("Delete account that is not archived")
    void shouldThrowExceptionWhenDeleteAccountThatIsNotArchived() {
        Account account = new Account();
        account.setArchived(false);
        account.setBalance("0");
        String accountId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        when(accountRepository.findByIdAndUserId(UUID.fromString(accountId), userId)).thenReturn(java.util.Optional.of(account));

        assertThrows(BusinessErroToDeleteEntity.class, () -> accountService.delete(accountId, userId));
    }

}
