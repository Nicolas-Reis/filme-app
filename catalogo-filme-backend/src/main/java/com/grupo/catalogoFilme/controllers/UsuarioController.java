package com.grupo.catalogoFilme.controllers;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.grupo.catalogoFilme.dto.usuario.UsuarioResponseDTO;
import com.grupo.catalogoFilme.entities.Usuario;
import com.grupo.catalogoFilme.services.AuthorizationService;
import com.grupo.catalogoFilme.services.ImagemService;
import com.grupo.catalogoFilme.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/usuarios")
@Tag(name = "Usuários", description = "Cadastro, login e consulta de usuários")
public class UsuarioController {
    @Autowired private UsuarioService service;
    @Autowired private AuthorizationService authorizationService;
    @Autowired private ImagemService imagemService;

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

    @GetMapping(value = "/me/imagem")
    @Operation(summary = "Exibe a imagem do usuário autenticado", description = "Retorna o arquivo de imagem (bytes) do próprio usuário logado para visualização/renderização")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Imagem retornada", content = @Content(mediaType = "image/*")),
        @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
        @ApiResponse(responseCode = "404", description = "Imagem não encontrada") })
    public ResponseEntity<byte[]> verImagem(){
        ImagemService.ImagemBinaria img = imagemService.baixar(authorizationService.usuarioAutenticado().getUrlImage());
        return ResponseEntity.ok().contentType(img.contentType()).body(img.conteudo());
    }

    @PostMapping(value = "/me/imagem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Envia a imagem do usuário autenticado",
        description = "Faz upload da imagem para o Cloudinary e salva a URL no campo url_image do próprio usuário logado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Imagem enviada e URL salva"),
        @ApiResponse(responseCode = "400", description = "Arquivo inválido"),
        @ApiResponse(responseCode = "401", description = "Usuário não autenticado") })
    public ResponseEntity<UsuarioResponseDTO> enviarImagem(@RequestParam("file") MultipartFile file){
        Usuario autenticado = authorizationService.usuarioAutenticado();
        String url = imagemService.upload(file, "usuarios");
        return ResponseEntity.ok(service.atualizarImagem(autenticado, url));
    }

    @PutMapping(value = "/{id}/promover-admin")
    @Operation(summary = "Promove um usuário a administrador",
        description = "Restrito a ADMIN. Substitui a role do usuário (ROLE_USER) por ROLE_ADMIN, sem acumular as duas.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário promovido a administrador"),
        @ApiResponse(responseCode = "403", description = "Acesso restrito a administradores"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado") })
    public ResponseEntity<UsuarioResponseDTO> promoverParaAdmin(@PathVariable Integer id){
        authorizationService.exigirAdmin();
        return ResponseEntity.ok(service.promoverParaAdmin(id));
    }

}
