package com.grupo.catalogoFilme.services;



import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo.catalogoFilme.entities.Genero;
import com.grupo.catalogoFilme.exceptions.DadosInvalidosException;
import com.grupo.catalogoFilme.exceptions.GeneroJaExisteException;
import com.grupo.catalogoFilme.exceptions.RegistroNaoEncontradoException;
import com.grupo.catalogoFilme.repositories.GeneroRepository;

@Service
public class GeneroService {
	@Autowired private GeneroRepository generoRepositorio;

	public List<Genero> procurarGenero() { return generoRepositorio.findAll(); }
	
	public Genero procurarPorId(Long id) {
		return generoRepositorio.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Gênero não encontrado"));
	}

	public String criarGenero(Genero genero) {
		if(genero.getTpGenero() == null || genero.getTpGenero().isBlank()) throw new DadosInvalidosException("ERRO! Nome obrigatório");
        if (generoRepositorio.findAll().stream().anyMatch(g -> g.getTpGenero().equalsIgnoreCase(genero.getTpGenero()))) throw new GeneroJaExisteException("Gênero já existe.");
		
		generoRepositorio.save(genero);
		return "Gênero salvo!";
	}

	public String atualizarGenero(Long id, Genero genero) {
		Genero existing = procurarPorId(id);
		existing.setTpGenero(genero.getTpGenero());
		generoRepositorio.save(existing);
		return "Gênero atualizado!";
	}
	
	public String excluirGenero(Long id) {
		Genero genero = generoRepositorio.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Gênero não encontrado"));
	    if (genero.getFilmes() != null && !genero.getFilmes().isEmpty()) throw new DadosInvalidosException("Não é possível deletar gênero associado a filmes.");
		generoRepositorio.deleteById(id);
		return "Gênero deletado";
	}
}