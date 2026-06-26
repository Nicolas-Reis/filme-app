package com.grupo.catalogoFilme.services;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo.catalogoFilme.dto.plataforma.PlataformaCreateDTO;
import com.grupo.catalogoFilme.dto.plataforma.PlataformaResponseDTO;
import com.grupo.catalogoFilme.dto.plataforma.PlataformaUpdateDTO;
import com.grupo.catalogoFilme.entities.Plataforma;
import com.grupo.catalogoFilme.enums.StatusRegistro;
import com.grupo.catalogoFilme.exceptions.DadosInvalidosException;
import com.grupo.catalogoFilme.exceptions.PlataformaJaExisteException;
import com.grupo.catalogoFilme.exceptions.RegistroNaoEncontradoException;
import com.grupo.catalogoFilme.mapper.PlataformaMapper;
import com.grupo.catalogoFilme.repositories.PlataformaRepository;

@Service
public class PlataformaService {
    @Autowired private PlataformaRepository plataformaRepositorio;
    @Autowired private PlataformaMapper plataformaMapper;

	public List<PlataformaResponseDTO> procurarPlataforma() {
		return plataformaRepositorio.findAllByStatusNot(StatusRegistro.INATIVO).stream().map(plataformaMapper::toDTO).toList();
	}

	public List<PlataformaResponseDTO> findAll() {
		return plataformaRepositorio.findAll().stream().map(plataformaMapper::toDTO).toList();
	}

	public PlataformaResponseDTO procurarPorId(Integer id) {
		return plataformaMapper.toDTO(buscarAtivo(id));
	}

	public PlataformaResponseDTO criarPlataforma(PlataformaCreateDTO dto) {
        if (plataformaRepositorio.findAll().stream().anyMatch(p -> p.getNome().equalsIgnoreCase(dto.getNome())))
        	throw new PlataformaJaExisteException("Plataforma já existe.");
		Plataforma plataforma = plataformaMapper.toEntity(dto);
		plataforma.setStatus(StatusRegistro.ATIVO);
		return plataformaMapper.toDTO(plataformaRepositorio.save(plataforma));
	}

	public PlataformaResponseDTO atualizar(Integer id, PlataformaUpdateDTO dto) {
        Plataforma existente = buscarAtivo(id);
        if (dto.getNome() != null && !dto.getNome().isBlank()) existente.setNome(dto.getNome());
        if (dto.getUrlImage() != null) existente.setUrlImage(dto.getUrlImage());
        return plataformaMapper.toDTO(plataformaRepositorio.save(existente));
    }

	public PlataformaResponseDTO atualizarImagem(Integer id, String url) {
		Plataforma plataforma = buscarAtivo(id);
		plataforma.setUrlImage(url);
		return plataformaMapper.toDTO(plataformaRepositorio.save(plataforma));
	}

	public void excluirPlataforma(Integer id) {
		Plataforma plataforma = buscarAtivo(id);
		if (plataforma.getFilmes() != null && !plataforma.getFilmes().isEmpty()) throw new DadosInvalidosException("Não é possível deletar plataforma com filmes vinculados.");
		plataformaRepositorio.logicalDeleteById(id);
	}

	private Plataforma buscarAtivo(Integer id) {
		Plataforma plataforma = plataformaRepositorio.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Plataforma não encontrada"));
		if (plataforma.getStatus() == StatusRegistro.INATIVO) throw new RegistroNaoEncontradoException("Plataforma não encontrada");
		return plataforma;
	}
}
