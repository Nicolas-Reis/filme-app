package com.grupo.catalogoFilme.services;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo.catalogoFilme.dto.avaliacao.AvaliacaoCreateDTO;
import com.grupo.catalogoFilme.dto.avaliacao.AvaliacaoResponseDTO;
import com.grupo.catalogoFilme.dto.avaliacao.AvaliacaoUpdateDTO;
import com.grupo.catalogoFilme.entities.Avaliacao;
import com.grupo.catalogoFilme.enums.StatusRegistro;
import com.grupo.catalogoFilme.exceptions.DadosInvalidosException;
import com.grupo.catalogoFilme.exceptions.RegistroNaoEncontradoException;
import com.grupo.catalogoFilme.mapper.AvaliacaoMapper;
import com.grupo.catalogoFilme.repositories.AvaliacaoRepository;

@Service
public class AvaliacaoService {
    @Autowired private AvaliacaoRepository avaliacaoRepositorio;
    @Autowired private AvaliacaoMapper avaliacaoMapper;

	public List<AvaliacaoResponseDTO> procurarAvaliacoes() {
		return avaliacaoRepositorio.findAllByStatusNot(StatusRegistro.INATIVO).stream().map(avaliacaoMapper::toDTO).toList();
	}

	public List<AvaliacaoResponseDTO> findAll() {
		return avaliacaoRepositorio.findAll().stream().map(avaliacaoMapper::toDTO).toList();
	}

	public AvaliacaoResponseDTO procurarPorId(Integer id) {
		return avaliacaoMapper.toDTO(buscarAtivo(id));
	}

	public AvaliacaoResponseDTO criarAvaliacao(AvaliacaoCreateDTO dto) {
		if (dto.getNota() < 0 || dto.getNota() > 5) throw new DadosInvalidosException("A nota deve ser entre 0 a 5.");
		Avaliacao avaliacao = avaliacaoMapper.toEntity(dto);
		avaliacao.setStatus(StatusRegistro.ATIVO);
		return avaliacaoMapper.toDTO(avaliacaoRepositorio.save(avaliacao));
	}

	public AvaliacaoResponseDTO atualizarAvaliacao(Integer id, AvaliacaoUpdateDTO dto) {
	    Avaliacao avaliacaoExistente = buscarAtivo(id);
	    if (dto.getNota() != null) {
	    	if (dto.getNota() < 0 || dto.getNota() > 5) throw new DadosInvalidosException("A nota deve ser entre 0 a 5.");
	    	avaliacaoExistente.setNota(dto.getNota());
	    }
	    if (dto.getComentario() != null) avaliacaoExistente.setComentario(dto.getComentario());
	    if (dto.getUrlImage() != null) avaliacaoExistente.setUrlImage(dto.getUrlImage());
	    return avaliacaoMapper.toDTO(avaliacaoRepositorio.save(avaliacaoExistente));
	}

	public void excluirAvaliacao(Integer id) {
		if (avaliacaoRepositorio.logicalDeleteById(id) == 0) throw new RegistroNaoEncontradoException("Avaliação não encontrada");
	}

	private Avaliacao buscarAtivo(Integer id) {
		Avaliacao avaliacao = avaliacaoRepositorio.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Avaliação não encontrada"));
		if (avaliacao.getStatus() == StatusRegistro.INATIVO) throw new RegistroNaoEncontradoException("Avaliação não encontrada");
		return avaliacao;
	}
}
