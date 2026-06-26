package com.grupo.catalogoFilme.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo.catalogoFilme.dto.genero.GeneroResponseDTO;
import com.grupo.catalogoFilme.services.GeneroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/generos")
@CrossOrigin(origins = "*")
@Tag(name = "Gêneros", description = "Consulta dos gêneros disponíveis (valores fixos do sistema)")
public class GeneroController {
	@Autowired private GeneroService service;

	@GetMapping
	@Operation(summary = "Lista os gêneros disponíveis", description = "Retorna a lista fixa de gêneros do sistema (código e nome)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso") })
	public ResponseEntity<List<GeneroResponseDTO>> procurarGenero(){
		return ResponseEntity.status(HttpStatus.OK).body(service.procurarGenero());
	}
}
