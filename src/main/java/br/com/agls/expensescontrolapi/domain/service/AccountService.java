package br.com.agls.expensescontrolapi.domain.service;

import br.com.agls.expensescontrolapi.domain.entity.Account;

import java.util.List;

public interface AccountService {

    void save(Account account);

    void archive(String accountId, String userId);

    void unarchive(String accountId, String userId);

    void delete(String accountId, String userId);

    List<Account> getAccountsByUserId(String userId);
}
