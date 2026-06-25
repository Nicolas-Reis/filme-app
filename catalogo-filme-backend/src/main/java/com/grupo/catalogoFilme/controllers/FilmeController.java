package com.grupo.catalogoFilme.controllers;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo.catalogoFilme.entities.Filme;
import com.grupo.catalogoFilme.services.FilmeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/filmes")
@CrossOrigin(origins = "*")
@Tag(name = "Filmes", description = "Operações de cadastro, consulta, atualização e exclusão (lógica) de filmes")
public class FilmeController {
	@Autowired private FilmeService service;

	@GetMapping
	@Operation(summary = "Lista os filmes ativos", description = "Retorna apenas os filmes com status ATIVO")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso") })
	public ResponseEntity<List<Filme>> procurarFilmes(){
		return ResponseEntity.status(HttpStatus.OK).body(service.procurarFilmes());
	}

	@GetMapping(value = "/todos")
	@Operation(summary = "Lista todos os filmes", description = "Retorna todos os filmes, incluindo os inativos (deletados logicamente)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso") })
	public ResponseEntity<List<Filme>> findAll(){
		return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	@Operation(summary = "Busca um filme por ID")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Filme encontrado"),
		@ApiResponse(responseCode = "404", description = "Filme não encontrado") })
	public ResponseEntity<Filme> procurarFilmeId(@PathVariable Integer id) {
		return ResponseEntity.ok(service.procurarFilmeId(id));
	}

	@PostMapping
	@Operation(summary = "Cadastra um novo filme")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Filme criado com sucesso"),
		@ApiResponse(responseCode = "400", description = "Dados inválidos"),
		@ApiResponse(responseCode = "404", description = "Gênero ou plataforma não encontrados"),
		@ApiResponse(responseCode = "409", description = "Filme já cadastrado") })
	public ResponseEntity<Filme> salvarFilme(@Valid @RequestBody Filme filme){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.salvarFilme(filme));
	}

	@PutMapping(value = "/{id}")
	@Operation(summary = "Atualiza um filme existente")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Filme atualizado"),
		@ApiResponse(responseCode = "404", description = "Filme não encontrado") })
	public ResponseEntity<Filme> atualizarFilme(@PathVariable Integer id, @RequestBody Filme filme){
		return ResponseEntity.ok(service.atualizarFilme(id, filme));
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Exclui (logicamente) um filme", description = "Marca o filme como INATIVO em vez de removê-lo do banco")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Filme excluído logicamente"),
		@ApiResponse(responseCode = "404", description = "Filme não encontrado") })
	public ResponseEntity<Void> excluirFilme(@PathVariable Integer id){
		service.excluirFilme(id);
		return ResponseEntity.noContent().build();
	}
}
