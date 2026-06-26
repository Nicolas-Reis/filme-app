package com.grupo.catalogoFilme.dto.filme;

import java.time.LocalDate;
import java.util.Set;

import com.grupo.catalogoFilme.enums.GeneroEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
	private String urlImage;
	@NotEmpty(message = "Informe ao menos um gênero")
	private Set<GeneroEnum> generos;
	@NotNull(message = "ID da plataforma é obrigatório")
	private Integer plataformaId;

	public FilmeCreateDTO() {}

	public FilmeCreateDTO(String titulo, String descricao, String diretor, LocalDate dataLancamento, String urlImage, Set<GeneroEnum> generos, Integer plataformaId) {
		this.titulo = titulo;
		this.descricao = descricao;
		this.diretor = diretor;
		this.dataLancamento = dataLancamento;
		this.urlImage = urlImage;
		this.generos = generos;
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

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public Set<GeneroEnum> getGeneros() {
		return generos;
	}

	public void setGeneros(Set<GeneroEnum> generos) {
		this.generos = generos;
	}

	public Integer getPlataformaId() {
		return plataformaId;
	}

	public void setPlataformaId(Integer plataformaId) {
		this.plataformaId = plataformaId;
	}
}
