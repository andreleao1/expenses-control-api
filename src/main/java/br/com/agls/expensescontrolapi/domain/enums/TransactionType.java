package br.com.agls.expensescontrolapi.domain.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TransactionType {
    CASH_IN("CASH_IN"),
    CASH_OUT("CASH_OUT");

    private final String value;
}
