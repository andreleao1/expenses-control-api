package br.com.agls.expensescontrolapi.domain.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TransactionStatus {
    MADE("MADE"),
    SCHEDULED("SCHEDULED"),
    CANCELED("CANCELED");

    private final String value;
}
