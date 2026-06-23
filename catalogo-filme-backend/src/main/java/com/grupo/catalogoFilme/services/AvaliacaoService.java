package com.grupo.catalogoFilme.services;


import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo.catalogoFilme.entities.Avaliacao;
import com.grupo.catalogoFilme.exceptions.DadosInvalidosException;
import com.grupo.catalogoFilme.exceptions.RegistroNaoEncontradoException;
import com.grupo.catalogoFilme.repositories.AvaliacaoRepository;
import com.grupo.catalogoFilme.repositories.FilmeRepository;

@Service
public class AvaliacaoService {
    @Autowired private AvaliacaoRepository avaliacaoRepositorio;
    @Autowired private FilmeRepository filmeRepositorio;

	public List<Avaliacao> procurarAvaliacoes() { return avaliacaoRepositorio.findAll(); }
	
	public Avaliacao procurarPorId(Long id) {
		return avaliacaoRepositorio.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Avaliação não encontrada"));
	}
	
	public String criarAvaliacao(Avaliacao avaliacao) {
		if (avaliacao.getNota() == null || avaliacao.getNota() < 0 || avaliacao.getNota() > 5) throw new DadosInvalidosException("A nota deve ser entre 0 a 5.");
		if (avaliacao.getComentario() == null || avaliacao.getComentario().isBlank()) throw new DadosInvalidosException("ERRO! É preciso comentar.");
		if (avaliacao.getDataAvaliacao() == null || avaliacao.getDataAvaliacao().isAfter(LocalDate.now())) throw new DadosInvalidosException("Erro! Data inválida.");
		if (avaliacao.getFilme() == null || avaliacao.getFilme().getId() == null) throw new DadosInvalidosException("Erro! ID do filme é obrigatório.");
		
		filmeRepositorio.findById(avaliacao.getFilme().getId()).orElseThrow(() -> new RegistroNaoEncontradoException("Filme não encontrado."));
		avaliacaoRepositorio.save(avaliacao);
		return "Avaliação salva com sucesso!";
	}

	public String atualizarAvaliacao(Long id, Avaliacao dadosNovos) {
	    Avaliacao avaliacaoExistente = procurarPorId(id);
	    if (dadosNovos.getNota() != null) avaliacaoExistente.setNota(dadosNovos.getNota());
	    if (dadosNovos.getComentario() != null) avaliacaoExistente.setComentario(dadosNovos.getComentario());
	    avaliacaoRepositorio.save(avaliacaoExistente);
	    return "Avaliação atualizada!";
	}

	public String excluirAvaliacao(Long id) {
		if(!avaliacaoRepositorio.existsById(id)) throw new RegistroNaoEncontradoException("Avaliação não encontrada");
		avaliacaoRepositorio.deleteById(id);
		return "Avaliação deletada";
	}
}