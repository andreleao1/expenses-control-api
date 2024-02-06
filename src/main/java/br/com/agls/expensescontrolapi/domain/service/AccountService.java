package br.com.agls.expensescontrolapi.domain.service;

import br.com.agls.expensescontrolapi.domain.entity.Account;

import java.util.List;

public interface AccountService {

    void save(Account account);

    void archive(String accountId);

    void unarchive(String accountId);

    void delete(String accountId);

    Account getAccountById(String accountId);

    List<Account> getAccountsPerUserId(String userId);
}
