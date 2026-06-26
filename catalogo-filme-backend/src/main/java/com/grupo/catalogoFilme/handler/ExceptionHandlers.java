package com.grupo.catalogoFilme.handler;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.grupo.catalogoFilme.exceptions.*;

@ControllerAdvice
public class ExceptionHandlers {
	@ExceptionHandler(RegistroNaoEncontradoException.class)
	public ResponseEntity<String> handleRegistroNaoEncontrado(RegistroNaoEncontradoException e){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); }
	@ExceptionHandler(DadosInvalidosException.class)
	public ResponseEntity<String> handleDadosInvalidosException(DadosInvalidosException e){ return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); }
	@ExceptionHandler(AcessoNegadoException.class)
	public ResponseEntity<String> handleAcessoNegado(AcessoNegadoException e){ return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage()); }
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<String> handleBadCredentials(BadCredentialsException e){ return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas."); }
	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<String> handleDisabled(DisabledException e){ return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuário inativo."); }
	@ExceptionHandler(PlataformaJaExisteException.class)
 public ResponseEntity<String> handlePlataformaJaExiste(PlataformaJaExisteException e) { return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); }
	@ExceptionHandler(FilmeJaFoiLogadoException.class)
 public ResponseEntity<String> handleFilmeJaFoiLoggado(FilmeJaFoiLogadoException e) { return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); }
	@ExceptionHandler(MaxUploadSizeExceededException.class)
 public ResponseEntity<String> handleMaxUploadSize(MaxUploadSizeExceededException e) { return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Imagem excede o tamanho máximo permitido."); }
}