package com.grupo.catalogoFilme.exceptions;

public class FilmeJaFoiLogadoException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public FilmeJaFoiLogadoException(String message) {
        super(message);
    }
}