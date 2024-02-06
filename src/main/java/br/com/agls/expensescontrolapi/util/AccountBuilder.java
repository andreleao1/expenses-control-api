package br.com.agls.expensescontrolapi.util;

import br.com.agls.expensescontrolapi.api.dto.in.AccountRequestDTO;
import br.com.agls.expensescontrolapi.domain.entity.Account;

import java.time.LocalDateTime;

public abstract class AccountBuilder {

    private AccountBuilder() {
    }

    public static Account execute(AccountRequestDTO accountRequestDTO) {
        return Account.builder()
                .userId(accountRequestDTO.getUserId())
                .name(accountRequestDTO.getName())
                .icon(accountRequestDTO.getIcon())
                .balance("0.0")
                .archived(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
