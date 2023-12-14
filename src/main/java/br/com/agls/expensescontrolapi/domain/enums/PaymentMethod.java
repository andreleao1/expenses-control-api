package br.com.agls.expensescontrolapi.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentMethod {
    CASH("CASH"),
    CREDIT_CARD("CREDIT_CARD"),
    DEBIT_CARD("DEBIT_CARD"),
    PIX("PIX"),
    TRANSFER("TRANSFER");

    private final String value;
}
