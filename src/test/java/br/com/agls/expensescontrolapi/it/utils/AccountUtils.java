package br.com.agls.expensescontrolapi.it.utils;

import br.com.agls.expensescontrolapi.domain.entity.Account;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountUtils {

    public static List<Account> generateAccounts() {

        List<Account> accounts = new ArrayList<>();

        for(int i = 0; i < 3; i++) {
            accounts.add(Account.builder()
                    .id(UUID.randomUUID())
                    .userId(UUID.randomUUID().toString())
                    .name("Account " + (i+1))
                    .icon("Icon " + (i+1))
                    .balance("1" + (i+1) + ".00")
                    .archived(Boolean.FALSE)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build());
        }
        return accounts;
    }
}
