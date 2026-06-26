package com.grupo.catalogoFilme.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo.catalogoFilme.dto.filme.FilmeCreateDTO;
import com.grupo.catalogoFilme.dto.filme.FilmeResponseDTO;
import com.grupo.catalogoFilme.dto.filme.FilmeUpdateDTO;
import com.grupo.catalogoFilme.entities.Filme;
import com.grupo.catalogoFilme.enums.StatusRegistro;
import com.grupo.catalogoFilme.exceptions.FilmeJaFoiLogadoException;
import com.grupo.catalogoFilme.exceptions.RegistroNaoEncontradoException;
import com.grupo.catalogoFilme.mapper.FilmeMapper;
import com.grupo.catalogoFilme.repositories.FilmeRepository;

@Service
public class FilmeService {
	@Autowired private FilmeRepository filmeRepositorio;
	@Autowired private FilmeMapper filmeMapper;

	public List<FilmeResponseDTO> procurarFilmes() {
		return filmeRepositorio.findAllByStatusNot(StatusRegistro.INATIVO).stream().map(filmeMapper::toDTO).toList();
	}

	public List<FilmeResponseDTO> findAll() {
		return filmeRepositorio.findAll().stream().map(filmeMapper::toDTO).toList();
	}

	public FilmeResponseDTO procurarFilmeId(Integer id) {
		return filmeMapper.toDTO(buscarAtivo(id));
	}

	public List<FilmeResponseDTO> procurarPorNome(String nome) {
		return filmeRepositorio.findByTituloContainingIgnoreCaseAndStatusNot(nome, StatusRegistro.INATIVO)
				.stream().map(filmeMapper::toDTO).toList();
	}

	public FilmeResponseDTO salvarFilme(FilmeCreateDTO dto) {
        boolean filmeJaExiste = filmeRepositorio.findAll().stream()
            .anyMatch(f -> f.getTitulo().equalsIgnoreCase(dto.getTitulo()) && f.getDiretor().equalsIgnoreCase(dto.getDiretor()) && f.getDataLancamento().equals(dto.getDataLancamento()));
        if (filmeJaExiste) throw new FilmeJaFoiLogadoException("ERRO! Já existe um filme cadastrado com esses dados.");

		Filme filme = filmeMapper.toEntity(dto);
		filme.setStatus(StatusRegistro.ATIVO);
		return filmeMapper.toDTO(filmeRepositorio.save(filme));
	}

	public FilmeResponseDTO atualizarFilme(Integer id, FilmeUpdateDTO dto) {
		Filme existingFilme = buscarAtivo(id);
		if (dto.getTitulo() != null && !dto.getTitulo().isBlank()) existingFilme.setTitulo(dto.getTitulo());
        if (dto.getDescricao() != null && !dto.getDescricao().isBlank()) existingFilme.setDescricao(dto.getDescricao());
        if (dto.getDiretor() != null && !dto.getDiretor().isBlank()) existingFilme.setDiretor(dto.getDiretor());
        if (dto.getDataLancamento() != null) existingFilme.setDataLancamento(dto.getDataLancamento());
        if (dto.getGeneros() != null && !dto.getGeneros().isEmpty()) existingFilme.setGeneros(dto.getGeneros());

        return filmeMapper.toDTO(filmeRepositorio.save(existingFilme));
	}

	public void excluirFilme(Integer id) {
		if (filmeRepositorio.logicalDeleteById(id) == 0) throw new RegistroNaoEncontradoException("O filme de id " + id + " não foi encontrado");
	}

	private Filme buscarAtivo(Integer id) {
		Filme filme = filmeRepositorio.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Filme com id " + id + " não encontrado"));
		if (filme.getStatus() == StatusRegistro.INATIVO) throw new RegistroNaoEncontradoException("Filme com id " + id + " não encontrado");
		return filme;
	}
}
