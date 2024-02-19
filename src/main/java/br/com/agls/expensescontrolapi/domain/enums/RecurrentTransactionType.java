package br.com.agls.expensescontrolapi.domain.enums;

public enum RecurrentTransactionType {

    FIXED("FIXED"),
    INSTALLMENT("INSTALLMENT");

    private final String value;

    RecurrentTransactionType(String value) {
        this.value = value;
    }
}
