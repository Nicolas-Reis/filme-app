package com.grupo.catalogoFilme.controllers;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.grupo.catalogoFilme.dto.filme.FilmeCreateDTO;
import com.grupo.catalogoFilme.dto.filme.FilmeResponseDTO;
import com.grupo.catalogoFilme.dto.filme.FilmeUpdateDTO;
import com.grupo.catalogoFilme.services.FilmeService;
import com.grupo.catalogoFilme.services.ImagemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/filmes")
@CrossOrigin(origins = "*")
@Tag(name = "Filmes", description = "Operações de cadastro, consulta, atualização e exclusão (lógica) de filmes")
public class FilmeController {
	@Autowired private FilmeService service;
	@Autowired private ImagemService imagemService;

	@GetMapping
	@Operation(summary = "Lista os filmes ativos", description = "Retorna apenas os filmes com status ATIVO")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso") })
	public ResponseEntity<List<FilmeResponseDTO>> procurarFilmes(){
		return ResponseEntity.status(HttpStatus.OK).body(service.procurarFilmes());
	}

	@GetMapping(value = "/todos")
	@Operation(summary = "Lista todos os filmes", description = "Retorna todos os filmes, incluindo os inativos (deletados logicamente)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso") })
	public ResponseEntity<List<FilmeResponseDTO>> findAll(){
		return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
	}

	@GetMapping(value = "/buscar")
	@Operation(summary = "Pesquisa filmes pelo nome", description = "Retorna os filmes ativos cujo título contém o termo informado (busca parcial, ignora maiúsculas/minúsculas)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso") })
	public ResponseEntity<List<FilmeResponseDTO>> procurarPorNome(@RequestParam String nome){
		return ResponseEntity.status(HttpStatus.OK).body(service.procurarPorNome(nome));
	}

	@GetMapping(value = "/{id}")
	@Operation(summary = "Busca um filme por ID")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Filme encontrado"),
		@ApiResponse(responseCode = "404", description = "Filme não encontrado") })
	public ResponseEntity<FilmeResponseDTO> procurarFilmeId(@PathVariable Integer id) {
		return ResponseEntity.ok(service.procurarFilmeId(id));
	}

	@PostMapping
	@Operation(summary = "Cadastra um novo filme")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Filme criado com sucesso"),
		@ApiResponse(responseCode = "400", description = "Dados inválidos"),
		@ApiResponse(responseCode = "404", description = "Gênero ou plataforma não encontrados"),
		@ApiResponse(responseCode = "409", description = "Filme já cadastrado") })
	public ResponseEntity<FilmeResponseDTO> salvarFilme(@Valid @RequestBody FilmeCreateDTO filme){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.salvarFilme(filme));
	}

	@PutMapping(value = "/{id}")
	@Operation(summary = "Atualiza um filme existente")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Filme atualizado"),
		@ApiResponse(responseCode = "404", description = "Filme não encontrado") })
	public ResponseEntity<FilmeResponseDTO> atualizarFilme(@PathVariable Integer id, @RequestBody FilmeUpdateDTO filme){
		return ResponseEntity.ok(service.atualizarFilme(id, filme));
	}

	@GetMapping(value = "/{id}/imagem")
	@Operation(summary = "Exibe a imagem do filme", description = "Retorna o arquivo de imagem (bytes) para visualização/renderização")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Imagem retornada", content = @Content(mediaType = "image/*")),
		@ApiResponse(responseCode = "404", description = "Filme ou imagem não encontrada") })
	public ResponseEntity<byte[]> verImagem(@PathVariable Integer id){
		ImagemService.ImagemBinaria img = imagemService.baixar(service.procurarFilmeId(id).getUrlImage());
		return ResponseEntity.ok().contentType(img.contentType()).body(img.conteudo());
	}

	@PostMapping(value = "/{id}/imagem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Envia a imagem do filme", description = "Restrito a ADMIN. Faz upload da imagem para o Cloudinary e salva a URL no campo url_image do filme")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Imagem enviada e URL salva"),
		@ApiResponse(responseCode = "400", description = "Arquivo inválido"),
		@ApiResponse(responseCode = "403", description = "Acesso restrito a administradores"),
		@ApiResponse(responseCode = "404", description = "Filme não encontrado") })
	public ResponseEntity<FilmeResponseDTO> enviarImagem(@PathVariable Integer id, @RequestParam("file") MultipartFile file){
		String url = imagemService.upload(file, "filmes");
		return ResponseEntity.ok(service.atualizarImagem(id, url));
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Exclui (logicamente) um filme", description = "Marca o filme como INATIVO em vez de removê-lo do banco")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Filme excluído logicamente"),
		@ApiResponse(responseCode = "404", description = "Filme não encontrado") })
	public ResponseEntity<Void> excluirFilme(@PathVariable Integer id){
		service.excluirFilme(id);
		return ResponseEntity.noContent().build();
	}
}
