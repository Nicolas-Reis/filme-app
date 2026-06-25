package com.grupo.catalogoFilme.dto.genero;

import jakarta.validation.constraints.NotBlank;

public class GeneroCreateDTO {

	@NotBlank(message = "Nome é obrigatório")
	private String nome;

	public GeneroCreateDTO() {}

	public GeneroCreateDTO(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
