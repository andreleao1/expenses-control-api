package br.com.agls.expensescontrolapi.domain.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PaymentMethod {
    CASH("CASH"),
    CREDIT_CARD("CREDIT_CARD"),
    DEBIT_CARD("DEBIT_CARD"),
    PIX("PIX"),
    TRANSFER("TRANSFER");

    private final String value;
}
