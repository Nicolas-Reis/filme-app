package com.grupo.catalogoFilme.handler;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.grupo.catalogoFilme.exceptions.*;

@ControllerAdvice
public class ExceptionHandlers {
	@ExceptionHandler(RegistroNaoEncontradoException.class)
	public ResponseEntity<String> handleRegistroNaoEncontrado(RegistroNaoEncontradoException e){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); }
	@ExceptionHandler(DadosInvalidosException.class)
	public ResponseEntity<String> handleDadosInvalidosException(DadosInvalidosException e){ return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); }
	@ExceptionHandler(GeneroJaExisteException.class)
 public ResponseEntity<String> handleGeneroJaExiste(GeneroJaExisteException e) { return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); }
	@ExceptionHandler(PlataformaJaExisteException.class)
 public ResponseEntity<String> handlePlataformaJaExiste(PlataformaJaExisteException e) { return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); }
	@ExceptionHandler(FilmeJaFoiLogadoException.class)
 public ResponseEntity<String> handleFilmeJaFoiLoggado(FilmeJaFoiLogadoException e) { return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); }
}