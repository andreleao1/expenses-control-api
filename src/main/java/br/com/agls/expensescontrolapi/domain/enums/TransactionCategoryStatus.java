package br.com.agls.expensescontrolapi.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TransactionCategoryStatus {

    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String value;
}
