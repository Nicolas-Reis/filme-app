package com.grupo.catalogoFilme.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo.catalogoFilme.entities.Filme;
import com.grupo.catalogoFilme.exceptions.DadosInvalidosException;
import com.grupo.catalogoFilme.exceptions.FilmeJaFoiLogadoException;
import com.grupo.catalogoFilme.exceptions.RegistroNaoEncontradoException;
import com.grupo.catalogoFilme.repositories.FilmeRepository;
import com.grupo.catalogoFilme.repositories.GeneroRepository;
import com.grupo.catalogoFilme.repositories.PlataformaRepository;

@Service
public class FilmeService {
	@Autowired private FilmeRepository filmeRepositorio;
	@Autowired private GeneroRepository generoRepositorio;
	@Autowired private PlataformaRepository plataformaRepositorio;

	public List<Filme> procurarFilmes() { return filmeRepositorio.findAll(); }

	public Filme procurarFilmeId(Long id) {
		return filmeRepositorio.findById(id).orElseThrow(() ->
        new RegistroNaoEncontradoException("Filme com id " + id + " não encontrado"));
	}

	public String salvarFilme(Filme filme) {
		if(filme.getTitulo() == null || filme.getTitulo().isBlank()) throw new DadosInvalidosException("ERRO! Título do filme é obrigatório");
		if(filme.getDiretor() == null || filme.getDiretor().isBlank()) throw new DadosInvalidosException("ERRO! Diretor do filme é obrigatório");
		if(filme.getAnoLancamento() == null || filme.getAnoLancamento() < 1888) throw new DadosInvalidosException("ERRO! Ano de lançamento inválido");
		
        if (filme.getGenero() == null || filme.getGenero().getId() == null) throw new DadosInvalidosException("ERRO! ID do gênero é obrigatório");
        Long generoId = filme.getGenero().getId();
        generoRepositorio.findById(generoId).orElseThrow(() -> new RegistroNaoEncontradoException("Não foi possível salvar: Gênero com ID " + generoId + " não encontrado."));

        if (filme.getPlataforma() == null || filme.getPlataforma().getId() == null) throw new DadosInvalidosException("ERRO! ID da plataforma é obrigatório");
        Long plataformaId = filme.getPlataforma().getId();
        plataformaRepositorio.findById(plataformaId).orElseThrow(() -> new RegistroNaoEncontradoException("Não foi possível salvar: Plataforma com ID " + plataformaId + " não encontrada."));
        
        boolean filmeJaExiste = filmeRepositorio.findAll().stream()
            .anyMatch(f -> f.getTitulo().equalsIgnoreCase(filme.getTitulo()) && f.getDiretor().equalsIgnoreCase(filme.getDiretor()) && f.getAnoLancamento().equals(filme.getAnoLancamento()));
        if (filmeJaExiste) throw new FilmeJaFoiLogadoException("ERRO! Já existe um filme cadastrado com esses dados.");

		filmeRepositorio.save(filme);
		return "Filme saved!";
	}

	public String atualizarFilme(Long id, Filme filme) {
		Filme existingFilme = filmeRepositorio.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Filme não encontrado"));
		if (filme.getTitulo() != null && !filme.getTitulo().isBlank()) existingFilme.setTitulo(filme.getTitulo());
        if (filme.getDiretor() != null && !filme.getDiretor().isBlank()) existingFilme.setDiretor(filme.getDiretor());
        if (filme.getAnoLancamento() != null && filme.getAnoLancamento() >= 1888) existingFilme.setAnoLancamento(filme.getAnoLancamento());
        
        filmeRepositorio.save(existingFilme);
		return "Filme atualizado com sucesso!";
	}

	public String excluirFilme(Long id) {
		if(!filmeRepositorio.existsById(id)) throw new RegistroNaoEncontradoException("O filme de id "+id+" não foi encontrado");
		filmeRepositorio.deleteById(id);
		return "Filme deletado com sucesso";
	}
}