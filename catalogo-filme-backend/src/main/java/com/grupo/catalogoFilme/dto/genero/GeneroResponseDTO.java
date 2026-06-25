package com.grupo.catalogoFilme.dto.genero;

import com.grupo.catalogoFilme.enums.StatusRegistro;

public class GeneroResponseDTO {

	private Integer id;
	private String nome;
	private StatusRegistro status;

	public GeneroResponseDTO() {}

	public GeneroResponseDTO(Integer id, String nome, StatusRegistro status) {
		this.id = id;
		this.nome = nome;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public StatusRegistro getStatus() {
		return status;
	}

	public void setStatus(StatusRegistro status) {
		this.status = status;
	}
}
