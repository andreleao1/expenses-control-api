package br.com.agls.expensescontrolapi.util;

import br.com.agls.expensescontrolapi.api.dto.in.AccountRequestDTO;
import br.com.agls.expensescontrolapi.api.dto.out.AccountResponseDTO;
import br.com.agls.expensescontrolapi.domain.entity.Account;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AccountUtils {

    private AccountUtils() {
    }

    public static Account buildAccount(AccountRequestDTO accountRequestDTO) {
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

    public static List<AccountResponseDTO> parseToAccountResponseDTO(List<Account> accounts) {
        return accounts.stream()
                .map(account -> AccountResponseDTO.builder()
                        .id(account.getId().toString())
                        .userId(account.getUserId())
                        .name(account.getName())
                        .icon(account.getIcon())
                        .balance(account.getBalance())
                        .archived(account.getArchived())
                        .build())
                .collect(Collectors.toList());
    }
}
