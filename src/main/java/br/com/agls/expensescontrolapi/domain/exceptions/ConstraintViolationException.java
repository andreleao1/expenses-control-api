package br.com.agls.expensescontrolapi.domain.exceptions;

public class ConstraintViolationException extends RuntimeException {

    public ConstraintViolationException(String message) {
        super(message);
    }
}
