package com.grupo.catalogoFilme.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo.catalogoFilme.services.UsuarioService;
import com.grupo.catalogoFilme.entities.Usuario;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired private UsuarioService service;

    @Operation(summary = "Realiza login simples", description = "Valida o par de credenciais digitado no app mobile.")
    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario loginRequest) {
        return ResponseEntity.ok(service.login(loginRequest.getUsername(), loginRequest.getSenha()));
    }

    @Operation(summary = "Cadastra um novo usuário")
    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrar(@Valid @RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(usuario));
    }
}