package com.grupo.catalogoFilme.exceptions;

public class PlataformaJaExisteException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public PlataformaJaExisteException(String message) {
        super(message);
    }
}