package com.grupo.catalogoFilme.services;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo.catalogoFilme.entities.Plataforma;
import com.grupo.catalogoFilme.exceptions.DadosInvalidosException;
import com.grupo.catalogoFilme.exceptions.PlataformaJaExisteException;
import com.grupo.catalogoFilme.exceptions.RegistroNaoEncontradoException;
import com.grupo.catalogoFilme.repositories.PlataformaRepository;

@Service
public class PlataformaService {
    @Autowired private PlataformaRepository plataformaRepositorio;

	public List<Plataforma> procurarPlataforma() { return plataformaRepositorio.findAll(); }
     
	public Plataforma procurarPorId(Long id) {
		return plataformaRepositorio.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Plataforma não encontrada"));
	}

	public String criarPlataforma(Plataforma plataforma) {
		if(plataforma.getNome() == null || plataforma.getNome().isBlank()) throw new DadosInvalidosException("ERRO! Nome obrigatório");
        if (plataformaRepositorio.findAll().stream().anyMatch(p -> p.getNome().equalsIgnoreCase(plataforma.getNome()))) throw new PlataformaJaExisteException("Plataforma já existe.");
		plataformaRepositorio.save(plataforma);
		return "Plataforma salva!";
	}

	public String atualizar(Long id, Plataforma dadosNovos) {
        Plataforma existente = procurarPorId(id);
        existente.setNome(dadosNovos.getNome());
        plataformaRepositorio.save(existente);
        return "Plataforma atualizada!";
    }
	
	public String excluirPlataforma(Long id) {
		Plataforma plataforma = plataformaRepositorio.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Plataforma não encontrada"));
		if (plataforma.getFilmes() != null && !plataforma.getFilmes().isEmpty()) throw new DadosInvalidosException("Não é possível deletar plataforma com filmes vinculados.");
		plataformaRepositorio.deleteById(id);
		return "Plataforma deletada";
	}
}