package com.grupo.catalogoFilme.dto.genero;

import jakarta.validation.constraints.NotBlank;

public class GeneroUpdateDTO {

	@NotBlank(message = "Nome é obrigatório")
	private String nome;

	public GeneroUpdateDTO() {}

	public GeneroUpdateDTO(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
