package com.grupo.catalogoFilme.services;



import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo.catalogoFilme.entities.Genero;
import com.grupo.catalogoFilme.enums.StatusRegistro;
import com.grupo.catalogoFilme.exceptions.DadosInvalidosException;
import com.grupo.catalogoFilme.exceptions.GeneroJaExisteException;
import com.grupo.catalogoFilme.exceptions.RegistroNaoEncontradoException;
import com.grupo.catalogoFilme.repositories.GeneroRepository;

@Service
public class GeneroService {
	@Autowired private GeneroRepository generoRepositorio;

	public List<Genero> procurarGenero() { return generoRepositorio.findAllByStatusNot(StatusRegistro.INATIVO); }

	public List<Genero> findAll() { return generoRepositorio.findAll(); }

	public Genero procurarPorId(Integer id) {
		Genero genero = generoRepositorio.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Gênero não encontrado"));
		if (genero.getStatus() == StatusRegistro.INATIVO) throw new RegistroNaoEncontradoException("Gênero não encontrado");
		return genero;
	}

	public Genero criarGenero(Genero genero) {
		if(genero.getNome() == null || genero.getNome().isBlank()) throw new DadosInvalidosException("ERRO! Nome obrigatório");
        if (generoRepositorio.findAll().stream().anyMatch(g -> g.getNome().equalsIgnoreCase(genero.getNome()))) throw new GeneroJaExisteException("Gênero já existe.");

		genero.setStatus(StatusRegistro.ATIVO);
		return generoRepositorio.save(genero);
	}

	public Genero atualizarGenero(Integer id, Genero genero) {
		Genero existing = procurarPorId(id);
		existing.setNome(genero.getNome());
		return generoRepositorio.save(existing);
	}

	public void excluirGenero(Integer id) {
		Genero genero = procurarPorId(id);
	    if (genero.getFilmes() != null && !genero.getFilmes().isEmpty()) throw new DadosInvalidosException("Não é possível deletar gênero associado a filmes.");
		generoRepositorio.logicalDeleteById(id);
	}
}