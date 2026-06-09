package com.af.estrategiafinanciera.domain.exception;

public class DuplicateResourceException extends BusinessException{
    public DuplicateResourceException(String resource, String field, String value) {
        super("Ya existe un/a " + resource + " con " + field + ": " + value);
    }
}
