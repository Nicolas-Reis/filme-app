package com.grupo.catalogoFilme.dto.filme;

import java.time.LocalDate;

public class FilmeUpdateDTO {

	private String titulo;
	private String descricao;
	private String diretor;
	private LocalDate dataLancamento;

	public FilmeUpdateDTO() {}

	public FilmeUpdateDTO(String titulo, String descricao, String diretor, LocalDate dataLancamento) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.diretor = diretor;
		this.dataLancamento = dataLancamento;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDiretor() {
		return diretor;
	}

	public void setDiretor(String diretor) {
		this.diretor = diretor;
	}

	public LocalDate getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(LocalDate dataLancamento) {
		this.dataLancamento = dataLancamento;
	}
}
