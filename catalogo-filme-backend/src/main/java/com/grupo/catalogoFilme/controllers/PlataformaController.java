package com.grupo.catalogoFilme.controllers;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.grupo.catalogoFilme.dto.plataforma.PlataformaCreateDTO;
import com.grupo.catalogoFilme.dto.plataforma.PlataformaResponseDTO;
import com.grupo.catalogoFilme.dto.plataforma.PlataformaUpdateDTO;
import com.grupo.catalogoFilme.services.ImagemService;
import com.grupo.catalogoFilme.services.PlataformaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/plataformas")
@CrossOrigin(origins = "*")
@Tag(name = "Plataformas", description = "Operações de cadastro, consulta, atualização e exclusão (lógica) de plataformas")
public class PlataformaController {
	@Autowired private PlataformaService service;
	@Autowired private ImagemService imagemService;

	@GetMapping
	@Operation(summary = "Lista as plataformas ativas", description = "Retorna apenas as plataformas com status ATIVO")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso") })
	public ResponseEntity<List<PlataformaResponseDTO>> procurarPlataforma(){
		return ResponseEntity.status(HttpStatus.OK).body(service.procurarPlataforma());
	}

	@GetMapping(value = "/todos")
	@Operation(summary = "Lista todas as plataformas", description = "Retorna todas as plataformas, incluindo as inativas (deletadas logicamente)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso") })
	public ResponseEntity<List<PlataformaResponseDTO>> findAll(){
		return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
	}

	@GetMapping(value = "/{id}")
	@Operation(summary = "Busca uma plataforma por ID")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Plataforma encontrada"),
		@ApiResponse(responseCode = "404", description = "Plataforma não encontrada") })
	public ResponseEntity<PlataformaResponseDTO> procurarPorId(@PathVariable Integer id) {
	    return ResponseEntity.ok(service.procurarPorId(id));
	}

	@PostMapping
	@Operation(summary = "Cadastra uma nova plataforma")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Plataforma criada com sucesso"),
		@ApiResponse(responseCode = "400", description = "Dados inválidos"),
		@ApiResponse(responseCode = "409", description = "Plataforma já existe") })
	public ResponseEntity<PlataformaResponseDTO> criarPlataforma(@Valid @RequestBody PlataformaCreateDTO plataforma){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.criarPlataforma(plataforma));
	}

	@PutMapping(value = "/{id}")
	@Operation(summary = "Atualiza uma plataforma existente")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Plataforma atualizada"),
		@ApiResponse(responseCode = "404", description = "Plataforma não encontrada") })
	public ResponseEntity<PlataformaResponseDTO> atualizar(@PathVariable Integer id, @RequestBody PlataformaUpdateDTO plataforma){
		return ResponseEntity.ok(service.atualizar(id, plataforma));
	}

	@GetMapping(value = "/{id}/imagem")
	@Operation(summary = "Exibe a imagem da plataforma", description = "Retorna o arquivo de imagem (bytes) para visualização/renderização")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Imagem retornada", content = @Content(mediaType = "image/*")),
		@ApiResponse(responseCode = "404", description = "Plataforma ou imagem não encontrada") })
	public ResponseEntity<byte[]> verImagem(@PathVariable Integer id){
		ImagemService.ImagemBinaria img = imagemService.baixar(service.procurarPorId(id).getUrlImage());
		return ResponseEntity.ok().contentType(img.contentType()).body(img.conteudo());
	}

	@PostMapping(value = "/{id}/imagem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Envia a imagem da plataforma", description = "Restrito a ADMIN. Faz upload da imagem para o Cloudinary e salva a URL no campo url_image da plataforma")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Imagem enviada e URL salva"),
		@ApiResponse(responseCode = "400", description = "Arquivo inválido"),
		@ApiResponse(responseCode = "403", description = "Acesso restrito a administradores"),
		@ApiResponse(responseCode = "404", description = "Plataforma não encontrada") })
	public ResponseEntity<PlataformaResponseDTO> enviarImagem(@PathVariable Integer id, @RequestParam("file") MultipartFile file){
		String url = imagemService.upload(file, "plataformas");
		return ResponseEntity.ok(service.atualizarImagem(id, url));
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Exclui (logicamente) uma plataforma", description = "Marca a plataforma como INATIVA em vez de removê-la do banco")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "Plataforma excluída logicamente"),
		@ApiResponse(responseCode = "400", description = "Plataforma com filmes vinculados"),
		@ApiResponse(responseCode = "404", description = "Plataforma não encontrada") })
	public ResponseEntity<Void> excluirPlataforma(@PathVariable Integer id){
		service.excluirPlataforma(id);
		return ResponseEntity.noContent().build();
	}
}
