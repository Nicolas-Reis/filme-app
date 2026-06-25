package com.grupo.catalogoFilme.controllers;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo.catalogoFilme.dto.usuario.UsuarioCreateDTO;
import com.grupo.catalogoFilme.dto.usuario.UsuarioLoginDTO;
import com.grupo.catalogoFilme.dto.usuario.UsuarioResponseDTO;
import com.grupo.catalogoFilme.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/usuarios")
@CrossOrigin(origins = "*")
@Tag(name = "Usuários", description = "Cadastro, login e consulta de usuários")
public class UsuarioController {
    @Autowired private UsuarioService service;

    @GetMapping
    @Operation(summary = "Lista os usuários ativos", description = "Retorna apenas os usuários com status ATIVO (sem expor a senha)")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso") })
    public ResponseEntity<List<UsuarioResponseDTO>> listar(){
        return ResponseEntity.status(HttpStatus.OK).body(service.listar());
    }

    @GetMapping(value = "/todos")
    @Operation(summary = "Lista todos os usuários", description = "Retorna todos os usuários, incluindo os inativos (deletados logicamente)")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso") })
    public ResponseEntity<List<UsuarioResponseDTO>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Busca um usuário por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado") })
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Integer id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping(value = "/cadastro")
    @Operation(summary = "Cadastra um novo usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou e-mail indisponível"),
        @ApiResponse(responseCode = "404", description = "Cargo não encontrado") })
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@Valid @RequestBody UsuarioCreateDTO usuario){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(usuario));
    }

    @PostMapping(value = "/login")
    @Operation(summary = "Realiza login", description = "Valida e-mail e senha e retorna os dados do usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Credenciais inválidas"),
        @ApiResponse(responseCode = "404", description = "Usuário não cadastrado") })
    public ResponseEntity<UsuarioResponseDTO> login(@Valid @RequestBody UsuarioLoginDTO login){
        return ResponseEntity.ok(service.login(login.getEmail(), login.getSenha()));
    }
}
