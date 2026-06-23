package com.grupo.catalogoFilme.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo.catalogoFilme.entities.Avaliacao;
import com.grupo.catalogoFilme.services.AvaliacaoService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/avaliacoes")
@CrossOrigin(origins = "*")
public class AvaliacaoController {
	@Autowired private AvaliacaoService service;
	//get
	@Operation(summary = "Listar todas as avaliações", description = "	Esse metodo retorna uma lista de avaliações")
	@GetMapping
	public ResponseEntity<List<Avaliacao>> procurarAvaliacoes(){
		return ResponseEntity.status(HttpStatus.OK).body(service.procurarAvaliacoes());
	}
	@Operation(summary = "Busca a avaliação por ID", description = "	Esse metodo retorna uma avaliação através da consulta por ID")
	@GetMapping(value = "/{id}")
	public ResponseEntity<Avaliacao> procurarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(service.procurarPorId(id));
	}
	@Operation(summary = "Cria a avaliação", description = " Esse método cria uma nova avaliação para um filme, incluindo nota (0-5), comentário e data de avaliação.  ")
	@PostMapping
	public ResponseEntity<String> criarAvaliacao(@Valid @RequestBody Avaliacao avaliacao){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.criarAvaliacao(avaliacao));
	}

	@PutMapping(value = "/{id}")
	@Operation(summary = "Atualiza a avaliação", description = " Esse método retorna os detalhes de uma avaliação específica com base no ID fornecido.  ")
	public ResponseEntity<String> atualizarAvaliacao(@PathVariable Long id, @RequestBody Avaliacao avaliacao){
		return ResponseEntity.ok(service.atualizarAvaliacao(id, avaliacao));
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Exclui a avaliação", description = " Esse método remove uma avaliação do sistema com base no ID fornecido.  ")
	public ResponseEntity<String> excluirAvaliacao(@PathVariable Long id){
		return ResponseEntity.status(HttpStatus.OK).body(service.excluirAvaliacao(id));
	}
}