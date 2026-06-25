package com.grupo.catalogoFilme.services;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo.catalogoFilme.entities.Avaliacao;
import com.grupo.catalogoFilme.enums.StatusRegistro;
import com.grupo.catalogoFilme.exceptions.DadosInvalidosException;
import com.grupo.catalogoFilme.exceptions.RegistroNaoEncontradoException;
import com.grupo.catalogoFilme.repositories.AvaliacaoRepository;
import com.grupo.catalogoFilme.repositories.FilmeRepository;
import com.grupo.catalogoFilme.repositories.UsuarioRepository;

@Service
public class AvaliacaoService {
    @Autowired private AvaliacaoRepository avaliacaoRepositorio;
    @Autowired private FilmeRepository filmeRepositorio;
    @Autowired private UsuarioRepository usuarioRepositorio;

	public List<Avaliacao> procurarAvaliacoes() { return avaliacaoRepositorio.findAllByStatusNot(StatusRegistro.INATIVO); }

	public List<Avaliacao> findAll() { return avaliacaoRepositorio.findAll(); }

	public Avaliacao procurarPorId(Integer id) {
		Avaliacao avaliacao = avaliacaoRepositorio.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Avaliação não encontrada"));
		if (avaliacao.getStatus() == StatusRegistro.INATIVO) throw new RegistroNaoEncontradoException("Avaliação não encontrada");
		return avaliacao;
	}

	public Avaliacao criarAvaliacao(Avaliacao avaliacao) {
		if (avaliacao.getNota() == null || avaliacao.getNota() < 0 || avaliacao.getNota() > 5) throw new DadosInvalidosException("A nota deve ser entre 0 a 5.");
		if (avaliacao.getComentario() == null || avaliacao.getComentario().isBlank()) throw new DadosInvalidosException("ERRO! É preciso comentar.");
		if (avaliacao.getFilme() == null || avaliacao.getFilme().getId() == null) throw new DadosInvalidosException("Erro! ID do filme é obrigatório.");
		if (avaliacao.getUsuario() == null || avaliacao.getUsuario().getId() == null) throw new DadosInvalidosException("Erro! ID do usuário é obrigatório.");

		filmeRepositorio.findById(avaliacao.getFilme().getId()).orElseThrow(() -> new RegistroNaoEncontradoException("Filme não encontrado."));
		usuarioRepositorio.findById(avaliacao.getUsuario().getId()).orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não encontrado."));
		avaliacao.setStatus(StatusRegistro.ATIVO);
		return avaliacaoRepositorio.save(avaliacao);
	}

	public Avaliacao atualizarAvaliacao(Integer id, Avaliacao dadosNovos) {
	    Avaliacao avaliacaoExistente = procurarPorId(id);
	    if (dadosNovos.getNota() != null) avaliacaoExistente.setNota(dadosNovos.getNota());
	    if (dadosNovos.getComentario() != null) avaliacaoExistente.setComentario(dadosNovos.getComentario());
	    if (dadosNovos.getUrlImage() != null) avaliacaoExistente.setUrlImage(dadosNovos.getUrlImage());
	    return avaliacaoRepositorio.save(avaliacaoExistente);
	}

	public void excluirAvaliacao(Integer id) {
		if (avaliacaoRepositorio.logicalDeleteById(id) == 0) throw new RegistroNaoEncontradoException("Avaliação não encontrada");
	}
}