package br.com.agls.expensescontrolapi.domain.exceptions;

public class ExpiredTokenException extends RuntimeException {

    public ExpiredTokenException(String message) {
        super(message);
    }
}
