package com.grupo.catalogoFilme.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.grupo.catalogoFilme.dto.filme.FilmeCreateDTO;
import com.grupo.catalogoFilme.dto.filme.FilmeResponseDTO;
import com.grupo.catalogoFilme.dto.genero.GeneroResponseDTO;
import com.grupo.catalogoFilme.entities.Filme;
import com.grupo.catalogoFilme.entities.Plataforma;
import com.grupo.catalogoFilme.exceptions.RegistroNaoEncontradoException;
import com.grupo.catalogoFilme.repositories.PlataformaRepository;

@Component
public class FilmeMapper {

	private final PlataformaRepository plataformaRepository;
	private final GeneroMapper generoMapper;
	private final PlataformaMapper plataformaMapper;

	public FilmeMapper(PlataformaRepository plataformaRepository, GeneroMapper generoMapper,
			PlataformaMapper plataformaMapper) {
		this.plataformaRepository = plataformaRepository;
		this.generoMapper = generoMapper;
		this.plataformaMapper = plataformaMapper;
	}

	public FilmeResponseDTO toDTO(Filme filme) {
		if (filme == null) return null;
		List<GeneroResponseDTO> generos = filme.getGeneros() == null ? List.of()
				: filme.getGeneros().stream().map(generoMapper::toDTO).toList();
		return new FilmeResponseDTO(filme.getId(), filme.getTitulo(), filme.getDescricao(), filme.getDiretor(),
				filme.getDataLancamento(), filme.getUrlImage(), filme.getStatus(), generos,
				plataformaMapper.toDTO(filme.getPlataforma()));
	}

	public Filme toEntity(FilmeCreateDTO dto) {
		Plataforma plataforma = plataformaRepository.findById(dto.getPlataformaId())
				.orElseThrow(() -> new RegistroNaoEncontradoException("Plataforma com ID " + dto.getPlataformaId() + " não encontrada."));
		Filme filme = new Filme();
		filme.setTitulo(dto.getTitulo());
		filme.setDescricao(dto.getDescricao());
		filme.setDiretor(dto.getDiretor());
		filme.setDataLancamento(dto.getDataLancamento());
		filme.setUrlImage(dto.getUrlImage());
		filme.setGeneros(dto.getGeneros());
		filme.setPlataforma(plataforma);
		return filme;
	}
}
