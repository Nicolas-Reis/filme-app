package com.grupo.catalogoFilme.controllers;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.grupo.catalogoFilme.dto.usuario.UsuarioResponseDTO;
import com.grupo.catalogoFilme.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/usuarios")
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

}
