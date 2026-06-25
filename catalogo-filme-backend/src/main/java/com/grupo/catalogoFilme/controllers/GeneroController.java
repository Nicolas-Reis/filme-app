package com.grupo.catalogoFilme.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo.catalogoFilme.entities.Genero;
import com.grupo.catalogoFilme.services.GeneroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/generos")
@CrossOrigin(origins = "*")
@Tag(name = "Gêneros", description = "Operações de cadastro, consulta, atualização e exclusão (lógica) de gêneros")
public class GeneroController {
	@Autowired private GeneroService service;

	@GetMapping
	@Operation(summary = "Lista os gêneros ativos", description = "Retorna apenas os gêneros com status ATIVO")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso") })
	public ResponseEntity<List<Genero>> procurarGenero(){
		return ResponseEntity.status(HttpStatus.OK).body(service.procurarGenero());
	}

	@GetMapping(value = "/todos")
	@Operation(summary = "Lista todos os gêneros", description = "Retorna todos os gêneros, incluindo os inativos (deletados logicamente)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso") })
	public ResponseEntity<List<Genero>> findAll(){
		return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	@Operation(summary = "Busca um gênero por ID")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Gênero encontrado"),
		@ApiResponse(responseCode = "404", description = "Gênero não encontrado") })
	public ResponseEntity<Genero> procurarPorId(@PathVariable Integer id) {
	    return ResponseEntity.ok(service.procurarPorId(id));
	}

	@PostMapping
	@Operation(summary = "Cadastra um novo gênero")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Gênero criado com sucesso"),
		@ApiResponse(responseCode = "400", description = "Dados inválidos"),
		@ApiResponse(responseCode = "409", description = "Gênero já existe") })
	public ResponseEntity<Genero> criarGenero(@Valid @RequestBody Genero genero){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.criarGenero(genero));
	}

	@PutMapping(value = "/{id}")
	@Operation(summary = "Atualiza um gênero existente")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Gênero atualizado"),
		@ApiResponse(responseCode = "404", description = "Gênero não encontrado") })
	public ResponseEntity<Genero> atualizarGenero(@PathVariable Integer id, @RequestBody Genero genero){
		return ResponseEntity.ok(service.atualizarGenero(id, genero));
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Exclui (logicamente) um gênero", description = "Marca o gênero como INATIVO em vez de removê-lo do banco")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Gênero excluído logicamente"),
		@ApiResponse(responseCode = "400", description = "Gênero associado a filmes"),
		@ApiResponse(responseCode = "404", description = "Gênero não encontrado") })
	public ResponseEntity<Void> excluirGenero(@PathVariable Integer id){
		service.excluirGenero(id);
		return ResponseEntity.noContent().build();
	}
}
