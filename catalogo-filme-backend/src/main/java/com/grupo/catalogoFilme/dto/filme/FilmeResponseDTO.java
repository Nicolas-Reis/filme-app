package com.grupo.catalogoFilme.dto.filme;

import java.time.LocalDate;

import com.grupo.catalogoFilme.dto.genero.GeneroResponseDTO;
import com.grupo.catalogoFilme.dto.plataforma.PlataformaResponseDTO;
import com.grupo.catalogoFilme.enums.StatusRegistro;

public class FilmeResponseDTO {

	private Integer id;
	private String titulo;
	private String descricao;
	private String diretor;
	private LocalDate dataLancamento;
	private StatusRegistro status;
	private GeneroResponseDTO genero;
	private PlataformaResponseDTO plataforma;

	public FilmeResponseDTO() {}

	public FilmeResponseDTO(Integer id, String titulo, String descricao, String diretor, LocalDate dataLancamento,
			StatusRegistro status, GeneroResponseDTO genero, PlataformaResponseDTO plataforma) {
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.diretor = diretor;
		this.dataLancamento = dataLancamento;
		this.status = status;
		this.genero = genero;
		this.plataforma = plataforma;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public StatusRegistro getStatus() {
		return status;
	}

	public void setStatus(StatusRegistro status) {
		this.status = status;
	}

	public GeneroResponseDTO getGenero() {
		return genero;
	}

	public void setGenero(GeneroResponseDTO genero) {
		this.genero = genero;
	}

	public PlataformaResponseDTO getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(PlataformaResponseDTO plataforma) {
		this.plataforma = plataforma;
	}
}
