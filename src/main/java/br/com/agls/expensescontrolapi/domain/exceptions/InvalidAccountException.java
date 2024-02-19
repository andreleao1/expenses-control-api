package br.com.agls.expensescontrolapi.domain.exceptions;

public class InvalidAccountException extends RuntimeException {

    public InvalidAccountException(String message) {
        super(message);
    }
}
