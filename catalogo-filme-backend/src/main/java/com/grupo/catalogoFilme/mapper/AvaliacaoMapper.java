package com.grupo.catalogoFilme.mapper;

import org.springframework.stereotype.Component;

import com.grupo.catalogoFilme.dto.avaliacao.AvaliacaoCreateDTO;
import com.grupo.catalogoFilme.dto.avaliacao.AvaliacaoResponseDTO;
import com.grupo.catalogoFilme.entities.Avaliacao;
import com.grupo.catalogoFilme.entities.Filme;
import com.grupo.catalogoFilme.entities.Usuario;
import com.grupo.catalogoFilme.exceptions.RegistroNaoEncontradoException;
import com.grupo.catalogoFilme.repositories.FilmeRepository;

@Component
public class AvaliacaoMapper {

	private final FilmeRepository filmeRepository;
	private final FilmeMapper filmeMapper;
	private final UsuarioMapper usuarioMapper;

	public AvaliacaoMapper(FilmeRepository filmeRepository, FilmeMapper filmeMapper, UsuarioMapper usuarioMapper) {
		this.filmeRepository = filmeRepository;
		this.filmeMapper = filmeMapper;
		this.usuarioMapper = usuarioMapper;
	}

	public AvaliacaoResponseDTO toDTO(Avaliacao avaliacao) {
		if (avaliacao == null) return null;
		return new AvaliacaoResponseDTO(avaliacao.getId(), avaliacao.getComentario(),
				avaliacao.getNota(), avaliacao.getStatus(), usuarioMapper.toDTO(avaliacao.getUsuario()),
				filmeMapper.toDTO(avaliacao.getFilme()));
	}

	public Avaliacao toEntity(AvaliacaoCreateDTO dto, Usuario usuario) {
		Filme filme = filmeRepository.findById(dto.getFilmeId())
				.orElseThrow(() -> new RegistroNaoEncontradoException("Filme não encontrado."));
		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setComentario(dto.getComentario());
		avaliacao.setNota(dto.getNota());
		avaliacao.setFilme(filme);
		avaliacao.setUsuario(usuario);
		return avaliacao;
	}
}
