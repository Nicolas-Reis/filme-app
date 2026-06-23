package com.grupo.catalogoFilme.controllers;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo.catalogoFilme.entities.Plataforma;
import com.grupo.catalogoFilme.services.PlataformaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/plataformas")
@CrossOrigin(origins = "*")
public class PlataformaController {
	@Autowired private PlataformaService service;
	
	@GetMapping
	public ResponseEntity<List<Plataforma>> procurarPlataforma(){
		return ResponseEntity.status(HttpStatus.OK).body(service.procurarPlataforma());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Plataforma> procurarPorId(@PathVariable Long id) {
	    return ResponseEntity.ok(service.procurarPorId(id));
	}
	
	@PostMapping
	public ResponseEntity<String> criarPlataforma(@Valid @RequestBody Plataforma plataforma){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.criarPlataforma(plataforma));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody Plataforma plataforma){
		return ResponseEntity.ok(service.atualizar(id, plataforma));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> excluirPlataforma(@PathVariable Long id){
		return ResponseEntity.status(HttpStatus.OK).body(service.excluirPlataforma(id));
	}
}