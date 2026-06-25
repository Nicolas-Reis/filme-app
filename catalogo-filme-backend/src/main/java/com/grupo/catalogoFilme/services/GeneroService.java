package com.grupo.catalogoFilme.services;



import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo.catalogoFilme.dto.genero.GeneroCreateDTO;
import com.grupo.catalogoFilme.dto.genero.GeneroResponseDTO;
import com.grupo.catalogoFilme.dto.genero.GeneroUpdateDTO;
import com.grupo.catalogoFilme.entities.Genero;
import com.grupo.catalogoFilme.enums.StatusRegistro;
import com.grupo.catalogoFilme.exceptions.DadosInvalidosException;
import com.grupo.catalogoFilme.exceptions.GeneroJaExisteException;
import com.grupo.catalogoFilme.exceptions.RegistroNaoEncontradoException;
import com.grupo.catalogoFilme.mapper.GeneroMapper;
import com.grupo.catalogoFilme.repositories.GeneroRepository;

@Service
public class GeneroService {
	@Autowired private GeneroRepository generoRepositorio;
	@Autowired private GeneroMapper generoMapper;

	public List<GeneroResponseDTO> procurarGenero() {
		return generoRepositorio.findAllByStatusNot(StatusRegistro.INATIVO).stream().map(generoMapper::toDTO).toList();
	}

	public List<GeneroResponseDTO> findAll() {
		return generoRepositorio.findAll().stream().map(generoMapper::toDTO).toList();
	}

	public GeneroResponseDTO procurarPorId(Integer id) {
		return generoMapper.toDTO(buscarAtivo(id));
	}

	public GeneroResponseDTO criarGenero(GeneroCreateDTO dto) {
		if (generoRepositorio.findAll().stream().anyMatch(g -> g.getNome().equalsIgnoreCase(dto.getNome())))
			throw new GeneroJaExisteException("Gênero já existe.");
		Genero genero = generoMapper.toEntity(dto);
		genero.setStatus(StatusRegistro.ATIVO);
		return generoMapper.toDTO(generoRepositorio.save(genero));
	}

	public GeneroResponseDTO atualizarGenero(Integer id, GeneroUpdateDTO dto) {
		Genero existing = buscarAtivo(id);
		existing.setNome(dto.getNome());
		return generoMapper.toDTO(generoRepositorio.save(existing));
	}

	public void excluirGenero(Integer id) {
		Genero genero = buscarAtivo(id);
	    if (genero.getFilmes() != null && !genero.getFilmes().isEmpty()) throw new DadosInvalidosException("Não é possível deletar gênero associado a filmes.");
		generoRepositorio.logicalDeleteById(id);
	}

	private Genero buscarAtivo(Integer id) {
		Genero genero = generoRepositorio.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Gênero não encontrado"));
		if (genero.getStatus() == StatusRegistro.INATIVO) throw new RegistroNaoEncontradoException("Gênero não encontrado");
		return genero;
	}
}
