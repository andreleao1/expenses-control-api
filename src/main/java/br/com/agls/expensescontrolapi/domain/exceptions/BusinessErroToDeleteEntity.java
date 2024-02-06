package br.com.agls.expensescontrolapi.domain.exceptions;

public class BusinessErroToDeleteEntity extends RuntimeException{

        public BusinessErroToDeleteEntity(String message) {
            super(message);
        }
}
