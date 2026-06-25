package com.grupo.catalogoFilme.controllers;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo.catalogoFilme.entities.Plataforma;
import com.grupo.catalogoFilme.services.PlataformaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/plataformas")
@CrossOrigin(origins = "*")
@Tag(name = "Plataformas", description = "Operações de cadastro, consulta, atualização e exclusão (lógica) de plataformas")
public class PlataformaController {
	@Autowired private PlataformaService service;

	@GetMapping
	@Operation(summary = "Lista as plataformas ativas", description = "Retorna apenas as plataformas com status ATIVO")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso") })
	public ResponseEntity<List<Plataforma>> procurarPlataforma(){
		return ResponseEntity.status(HttpStatus.OK).body(service.procurarPlataforma());
	}

	@GetMapping(value = "/todos")
	@Operation(summary = "Lista todas as plataformas", description = "Retorna todas as plataformas, incluindo as inativas (deletadas logicamente)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso") })
	public ResponseEntity<List<Plataforma>> findAll(){
		return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	@Operation(summary = "Busca uma plataforma por ID")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Plataforma encontrada"),
		@ApiResponse(responseCode = "404", description = "Plataforma não encontrada") })
	public ResponseEntity<Plataforma> procurarPorId(@PathVariable Integer id) {
	    return ResponseEntity.ok(service.procurarPorId(id));
	}

	@PostMapping
	@Operation(summary = "Cadastra uma nova plataforma")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Plataforma criada com sucesso"),
		@ApiResponse(responseCode = "400", description = "Dados inválidos"),
		@ApiResponse(responseCode = "409", description = "Plataforma já existe") })
	public ResponseEntity<Plataforma> criarPlataforma(@Valid @RequestBody Plataforma plataforma){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.criarPlataforma(plataforma));
	}

	@PutMapping(value = "/{id}")
	@Operation(summary = "Atualiza uma plataforma existente")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Plataforma atualizada"),
		@ApiResponse(responseCode = "404", description = "Plataforma não encontrada") })
	public ResponseEntity<Plataforma> atualizar(@PathVariable Integer id, @RequestBody Plataforma plataforma){
		return ResponseEntity.ok(service.atualizar(id, plataforma));
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Exclui (logicamente) uma plataforma", description = "Marca a plataforma como INATIVA em vez de removê-la do banco")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Plataforma excluída logicamente"),
		@ApiResponse(responseCode = "400", description = "Plataforma com filmes vinculados"),
		@ApiResponse(responseCode = "404", description = "Plataforma não encontrada") })
	public ResponseEntity<Void> excluirPlataforma(@PathVariable Integer id){
		service.excluirPlataforma(id);
		return ResponseEntity.noContent().build();
	}
}
