package com.grupo.catalogoFilme.services;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo.catalogoFilme.entities.Plataforma;
import com.grupo.catalogoFilme.enums.StatusRegistro;
import com.grupo.catalogoFilme.exceptions.DadosInvalidosException;
import com.grupo.catalogoFilme.exceptions.PlataformaJaExisteException;
import com.grupo.catalogoFilme.exceptions.RegistroNaoEncontradoException;
import com.grupo.catalogoFilme.repositories.PlataformaRepository;

@Service
public class PlataformaService {
    @Autowired private PlataformaRepository plataformaRepositorio;

	public List<Plataforma> procurarPlataforma() { return plataformaRepositorio.findAllByStatusNot(StatusRegistro.INATIVO); }

	public List<Plataforma> findAll() { return plataformaRepositorio.findAll(); }

	public Plataforma procurarPorId(Integer id) {
		Plataforma plataforma = plataformaRepositorio.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Plataforma não encontrada"));
		if (plataforma.getStatus() == StatusRegistro.INATIVO) throw new RegistroNaoEncontradoException("Plataforma não encontrada");
		return plataforma;
	}

	public Plataforma criarPlataforma(Plataforma plataforma) {
		if(plataforma.getNome() == null || plataforma.getNome().isBlank()) throw new DadosInvalidosException("ERRO! Nome obrigatório");
        if (plataformaRepositorio.findAll().stream().anyMatch(p -> p.getNome().equalsIgnoreCase(plataforma.getNome()))) throw new PlataformaJaExisteException("Plataforma já existe.");
		plataforma.setStatus(StatusRegistro.ATIVO);
		return plataformaRepositorio.save(plataforma);
	}

	public Plataforma atualizar(Integer id, Plataforma dadosNovos) {
        Plataforma existente = procurarPorId(id);
        existente.setNome(dadosNovos.getNome());
        if (dadosNovos.getUrlImage() != null) existente.setUrlImage(dadosNovos.getUrlImage());
        return plataformaRepositorio.save(existente);
    }

	public void excluirPlataforma(Integer id) {
		Plataforma plataforma = procurarPorId(id);
		if (plataforma.getFilmes() != null && !plataforma.getFilmes().isEmpty()) throw new DadosInvalidosException("Não é possível deletar plataforma com filmes vinculados.");
		plataformaRepositorio.logicalDeleteById(id);
	}
}