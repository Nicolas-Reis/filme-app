package com.grupo.catalogoFilme.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo.catalogoFilme.services.UsuarioService;
import com.grupo.catalogoFilme.entities.Usuario;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Autenticação", description = "Login e cadastro de usuários")
public class AuthController {
    @Autowired private UsuarioService service;

    @Operation(summary = "Realiza login simples", description = "Valida o par de credenciais (e-mail e senha) e retorna os dados do usuário.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Credenciais inválidas"),
        @ApiResponse(responseCode = "404", description = "Usuário não cadastrado") })
    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario loginRequest) {
        return ResponseEntity.ok(service.login(loginRequest.getEmail(), loginRequest.getSenha()));
    }

    @Operation(summary = "Cadastra um novo usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou e-mail indisponível") })
    @PostMapping("/cadastro")
    public ResponseEntity<Usuario> cadastrar(@Valid @RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(usuario));
    }
}
