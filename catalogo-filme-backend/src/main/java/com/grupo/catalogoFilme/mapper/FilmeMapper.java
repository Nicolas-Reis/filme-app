package com.grupo.catalogoFilme.mapper;

import org.springframework.stereotype.Component;

import com.grupo.catalogoFilme.dto.filme.FilmeCreateDTO;
import com.grupo.catalogoFilme.dto.filme.FilmeResponseDTO;
import com.grupo.catalogoFilme.entities.Filme;
import com.grupo.catalogoFilme.entities.Genero;
import com.grupo.catalogoFilme.entities.Plataforma;
import com.grupo.catalogoFilme.exceptions.RegistroNaoEncontradoException;
import com.grupo.catalogoFilme.repositories.GeneroRepository;
import com.grupo.catalogoFilme.repositories.PlataformaRepository;

@Component
public class FilmeMapper {

	private final GeneroRepository generoRepository;
	private final PlataformaRepository plataformaRepository;
	private final GeneroMapper generoMapper;
	private final PlataformaMapper plataformaMapper;

	public FilmeMapper(GeneroRepository generoRepository, PlataformaRepository plataformaRepository,
			GeneroMapper generoMapper, PlataformaMapper plataformaMapper) {
		this.generoRepository = generoRepository;
		this.plataformaRepository = plataformaRepository;
		this.generoMapper = generoMapper;
		this.plataformaMapper = plataformaMapper;
	}

	public FilmeResponseDTO toDTO(Filme filme) {
		if (filme == null) return null;
		return new FilmeResponseDTO(filme.getId(), filme.getTitulo(), filme.getDescricao(), filme.getDiretor(),
				filme.getDataLancamento(), filme.getStatus(), generoMapper.toDTO(filme.getGenero()),
				plataformaMapper.toDTO(filme.getPlataforma()));
	}

	public Filme toEntity(FilmeCreateDTO dto) {
		Genero genero = generoRepository.findById(dto.getGeneroId())
				.orElseThrow(() -> new RegistroNaoEncontradoException("Gênero com ID " + dto.getGeneroId() + " não encontrado."));
		Plataforma plataforma = plataformaRepository.findById(dto.getPlataformaId())
				.orElseThrow(() -> new RegistroNaoEncontradoException("Plataforma com ID " + dto.getPlataformaId() + " não encontrada."));
		Filme filme = new Filme();
		filme.setTitulo(dto.getTitulo());
		filme.setDescricao(dto.getDescricao());
		filme.setDiretor(dto.getDiretor());
		filme.setDataLancamento(dto.getDataLancamento());
		filme.setGenero(genero);
		filme.setPlataforma(plataforma);
		return filme;
	}
}
