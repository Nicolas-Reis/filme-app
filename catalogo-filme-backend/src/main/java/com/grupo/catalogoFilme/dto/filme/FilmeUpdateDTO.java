package com.grupo.catalogoFilme.dto.filme;

import java.time.LocalDate;
import java.util.Set;

import com.grupo.catalogoFilme.enums.GeneroEnum;

public class FilmeUpdateDTO {

	private String titulo;
	private String descricao;
	private String diretor;
	private LocalDate dataLancamento;
	private Set<GeneroEnum> generos;

	public FilmeUpdateDTO() {}

	public FilmeUpdateDTO(String titulo, String descricao, String diretor, LocalDate dataLancamento, Set<GeneroEnum> generos) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.diretor = diretor;
		this.dataLancamento = dataLancamento;
		this.generos = generos;
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

	public Set<GeneroEnum> getGeneros() {
		return generos;
	}

	public void setGeneros(Set<GeneroEnum> generos) {
		this.generos = generos;
	}
}
