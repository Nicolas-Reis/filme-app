package com.grupo.catalogoFilme.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo.catalogoFilme.entities.Avaliacao;
import com.grupo.catalogoFilme.services.AvaliacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/avaliacoes")
@CrossOrigin(origins = "*")
@Tag(name = "Avaliações", description = "Operações de cadastro, consulta, atualização e exclusão (lógica) de avaliações de filmes")
public class AvaliacaoController {
	@Autowired private AvaliacaoService service;

	@GetMapping
	@Operation(summary = "Lista as avaliações ativas", description = "Retorna apenas as avaliações com status ATIVO")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso") })
	public ResponseEntity<List<Avaliacao>> procurarAvaliacoes(){
		return ResponseEntity.status(HttpStatus.OK).body(service.procurarAvaliacoes());
	}

	@GetMapping(value = "/todos")
	@Operation(summary = "Lista todas as avaliações", description = "Retorna todas as avaliações, incluindo as inativas (deletadas logicamente)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso") })
	public ResponseEntity<List<Avaliacao>> findAll(){
		return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	@Operation(summary = "Busca uma avaliação por ID")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Avaliação encontrada"),
		@ApiResponse(responseCode = "404", description = "Avaliação não encontrada") })
	public ResponseEntity<Avaliacao> procurarPorId(@PathVariable Integer id) {
		return ResponseEntity.ok(service.procurarPorId(id));
	}

	@PostMapping
	@Operation(summary = "Cria uma avaliação", description = "Cria uma nova avaliação para um filme, com nota (0 a 5) e comentário")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Avaliação criada com sucesso"),
		@ApiResponse(responseCode = "400", description = "Dados inválidos"),
		@ApiResponse(responseCode = "404", description = "Filme ou usuário não encontrados") })
	public ResponseEntity<Avaliacao> criarAvaliacao(@Valid @RequestBody Avaliacao avaliacao){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.criarAvaliacao(avaliacao));
	}

	@PutMapping(value = "/{id}")
	@Operation(summary = "Atualiza uma avaliação existente")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Avaliação atualizada"),
		@ApiResponse(responseCode = "404", description = "Avaliação não encontrada") })
	public ResponseEntity<Avaliacao> atualizarAvaliacao(@PathVariable Integer id, @RequestBody Avaliacao avaliacao){
		return ResponseEntity.ok(service.atualizarAvaliacao(id, avaliacao));
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Exclui (logicamente) uma avaliação", description = "Marca a avaliação como INATIVA em vez de removê-la do banco")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Avaliação excluída logicamente"),
		@ApiResponse(responseCode = "404", description = "Avaliação não encontrada") })
	public ResponseEntity<Void> excluirAvaliacao(@PathVariable Integer id){
		service.excluirAvaliacao(id);
		return ResponseEntity.noContent().build();
	}
}
