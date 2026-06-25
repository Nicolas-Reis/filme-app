package com.grupo.catalogoFilme.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo.catalogoFilme.dto.auth.LoginResponseDTO;
import com.grupo.catalogoFilme.dto.usuario.UsuarioCreateDTO;
import com.grupo.catalogoFilme.dto.usuario.UsuarioLoginDTO;
import com.grupo.catalogoFilme.dto.usuario.UsuarioResponseDTO;
import com.grupo.catalogoFilme.services.AuthorizationService;
import com.grupo.catalogoFilme.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
@Tag(name = "Autenticação", description = "Login e cadastro de usuários")
public class AuthController {
    @Autowired private UsuarioService service;
    @Autowired private AuthorizationService authorizationService;

    @Operation(summary = "Realiza login e retorna um token JWT", description = "Valida o par de credenciais (e-mail e senha) e retorna o token de acesso.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
        @ApiResponse(responseCode = "403", description = "Usuário inativo") })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody UsuarioLoginDTO login) {
        return ResponseEntity.ok(authorizationService.autenticar(login));
    }

    @Operation(summary = "Cadastra um novo usuário", description = "Cria um usuário comum (ROLE_USER)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou e-mail indisponível"),
        @ApiResponse(responseCode = "404", description = "Cargo não encontrado") })
    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@Valid @RequestBody UsuarioCreateDTO usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(usuario));
    }
}
