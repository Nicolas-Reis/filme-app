package com.grupo.catalogoFilme.dto.plataforma;

import jakarta.validation.constraints.NotBlank;

public class PlataformaCreateDTO {

	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	private String urlImage;

	public PlataformaCreateDTO() {}

	public PlataformaCreateDTO(String nome, String urlImage) {
		this.nome = nome;
		this.urlImage = urlImage;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
}
