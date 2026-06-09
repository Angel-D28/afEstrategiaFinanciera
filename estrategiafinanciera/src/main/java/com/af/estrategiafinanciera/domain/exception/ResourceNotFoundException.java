package com.af.estrategiafinanciera.domain.exception;

public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String resource, Long id) {
        super(resource + "No encontrado con id: " + id);
    }

    public ResourceNotFoundException(String resource, String field, String value){
        super(resource + "no econtrado con " + field + ": " + value);
    }
}
