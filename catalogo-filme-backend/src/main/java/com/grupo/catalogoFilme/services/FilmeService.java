package com.grupo.catalogoFilme.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo.catalogoFilme.entities.Filme;
import com.grupo.catalogoFilme.enums.StatusRegistro;
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

	public List<Filme> procurarFilmes() { return filmeRepositorio.findAllByStatusNot(StatusRegistro.INATIVO); }

	public List<Filme> findAll() { return filmeRepositorio.findAll(); }

	public Filme procurarFilmeId(Integer id) {
		Filme filme = filmeRepositorio.findById(id).orElseThrow(() ->
        new RegistroNaoEncontradoException("Filme com id " + id + " não encontrado"));
		if (filme.getStatus() == StatusRegistro.INATIVO) throw new RegistroNaoEncontradoException("Filme com id " + id + " não encontrado");
		return filme;
	}

	public Filme salvarFilme(Filme filme) {
		if(filme.getTitulo() == null || filme.getTitulo().isBlank()) throw new DadosInvalidosException("ERRO! Título do filme é obrigatório");
		if(filme.getDescricao() == null || filme.getDescricao().isBlank()) throw new DadosInvalidosException("ERRO! Descrição do filme é obrigatória");
		if(filme.getDiretor() == null || filme.getDiretor().isBlank()) throw new DadosInvalidosException("ERRO! Diretor do filme é obrigatório");
		if(filme.getDataLancamento() == null) throw new DadosInvalidosException("ERRO! Data de lançamento inválida");

        if (filme.getGenero() == null || filme.getGenero().getId() == null) throw new DadosInvalidosException("ERRO! ID do gênero é obrigatório");
        Integer generoId = filme.getGenero().getId();
        generoRepositorio.findById(generoId).orElseThrow(() -> new RegistroNaoEncontradoException("Não foi possível salvar: Gênero com ID " + generoId + " não encontrado."));

        if (filme.getPlataforma() == null || filme.getPlataforma().getId() == null) throw new DadosInvalidosException("ERRO! ID da plataforma é obrigatório");
        Integer plataformaId = filme.getPlataforma().getId();
        plataformaRepositorio.findById(plataformaId).orElseThrow(() -> new RegistroNaoEncontradoException("Não foi possível salvar: Plataforma com ID " + plataformaId + " não encontrada."));

        boolean filmeJaExiste = filmeRepositorio.findAll().stream()
            .anyMatch(f -> f.getTitulo().equalsIgnoreCase(filme.getTitulo()) && f.getDiretor().equalsIgnoreCase(filme.getDiretor()) && f.getDataLancamento().equals(filme.getDataLancamento()));
        if (filmeJaExiste) throw new FilmeJaFoiLogadoException("ERRO! Já existe um filme cadastrado com esses dados.");

		filme.setStatus(StatusRegistro.ATIVO);
		return filmeRepositorio.save(filme);
	}

	public Filme atualizarFilme(Integer id, Filme filme) {
		Filme existingFilme = procurarFilmeId(id);
		if (filme.getTitulo() != null && !filme.getTitulo().isBlank()) existingFilme.setTitulo(filme.getTitulo());
        if (filme.getDescricao() != null && !filme.getDescricao().isBlank()) existingFilme.setDescricao(filme.getDescricao());
        if (filme.getDiretor() != null && !filme.getDiretor().isBlank()) existingFilme.setDiretor(filme.getDiretor());
        if (filme.getDataLancamento() != null) existingFilme.setDataLancamento(filme.getDataLancamento());

        return filmeRepositorio.save(existingFilme);
	}

	public void excluirFilme(Integer id) {
		if (filmeRepositorio.logicalDeleteById(id) == 0) throw new RegistroNaoEncontradoException("O filme de id " + id + " não foi encontrado");
	}
}