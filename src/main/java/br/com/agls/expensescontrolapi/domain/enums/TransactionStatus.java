package br.com.agls.expensescontrolapi.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TransactionStatus {
    MADE("MADE"),
    SCHEDULED("SCHEDULED"),
    CANCELED("CANCELED");

    private final String value;
}
