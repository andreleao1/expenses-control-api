package br.com.agls.expensescontrolapi.domain.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TransactionCategoryStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String value;
}
