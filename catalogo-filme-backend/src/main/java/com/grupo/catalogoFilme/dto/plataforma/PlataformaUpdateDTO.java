package com.grupo.catalogoFilme.dto.plataforma;

public class PlataformaUpdateDTO {

	private String nome;
	private String urlImage;

	public PlataformaUpdateDTO() {}

	public PlataformaUpdateDTO(String nome, String urlImage) {
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
