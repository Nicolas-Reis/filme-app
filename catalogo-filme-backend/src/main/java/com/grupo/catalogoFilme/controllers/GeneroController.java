package com.grupo.catalogoFilme.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo.catalogoFilme.entities.Genero;
import com.grupo.catalogoFilme.services.GeneroService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/generos")
@CrossOrigin(origins = "*")
public class GeneroController {
	@Autowired private GeneroService service;
	
	@GetMapping
	public ResponseEntity<List<Genero>> procurarGenero(){
		return ResponseEntity.status(HttpStatus.OK).body(service.procurarGenero());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Genero> procurarPorId(@PathVariable Long id) {
	    return ResponseEntity.ok(service.procurarPorId(id));
	}
	
	@PostMapping
	public ResponseEntity<String> criarGenero(@Valid @RequestBody Genero genero){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.criarGenero(genero));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<String> atualizarGenero(@PathVariable Long id, @RequestBody Genero genero){
		return ResponseEntity.ok(service.atualizarGenero(id, genero));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> excluirGenero(@PathVariable Long id){
		return ResponseEntity.status(HttpStatus.OK).body(service.excluirGenero(id));
	}
}