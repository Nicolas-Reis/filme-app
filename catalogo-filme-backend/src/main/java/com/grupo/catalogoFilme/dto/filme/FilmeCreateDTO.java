package com.grupo.catalogoFilme.dto.filme;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FilmeCreateDTO {

	@NotBlank(message = "Título é obrigatório")
	private String titulo;
	@NotBlank(message = "Descrição é obrigatória")
	private String descricao;
	@NotBlank(message = "Diretor é obrigatório")
	private String diretor;
	@NotNull(message = "Data de lançamento é obrigatória")
	private LocalDate dataLancamento;
	@NotNull(message = "ID do gênero é obrigatório")
	private Integer generoId;
	@NotNull(message = "ID da plataforma é obrigatório")
	private Integer plataformaId;

	public FilmeCreateDTO() {}

	public FilmeCreateDTO(String titulo, String descricao, String diretor, LocalDate dataLancamento, Integer generoId, Integer plataformaId) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.diretor = diretor;
		this.dataLancamento = dataLancamento;
		this.generoId = generoId;
		this.plataformaId = plataformaId;
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

	public Integer getGeneroId() {
		return generoId;
	}

	public void setGeneroId(Integer generoId) {
		this.generoId = generoId;
	}

	public Integer getPlataformaId() {
		return plataformaId;
	}

	public void setPlataformaId(Integer plataformaId) {
		this.plataformaId = plataformaId;
	}
}
