package com.grupo.catalogoFilme.controllers;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo.catalogoFilme.entities.Filme;
import com.grupo.catalogoFilme.services.FilmeService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/filmes")
@CrossOrigin(origins = "*")
public class FilmeController {
	@Autowired private FilmeService service;
	
	@GetMapping
	@Operation(summary = "Lista todos os filmes")
	public ResponseEntity<List<Filme>> procurarFilmes(){
		return ResponseEntity.status(HttpStatus.OK).body(service.procurarFilmes());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Filme> procurarFilmeId(@PathVariable Long id) {
		return ResponseEntity.ok(service.procurarFilmeId(id));
	}

	@PostMapping
	public ResponseEntity<String> salvarFilme(@Valid @RequestBody Filme filme){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.salvarFilme(filme));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<String> atualizarFilme(@PathVariable Long id, @RequestBody Filme filme){
		return ResponseEntity.ok(service.atualizarFilme(id, filme));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> excluirFilme(@PathVariable Long id){
		return ResponseEntity.status(HttpStatus.OK).body(service.excluirFilme(id));
	}
}