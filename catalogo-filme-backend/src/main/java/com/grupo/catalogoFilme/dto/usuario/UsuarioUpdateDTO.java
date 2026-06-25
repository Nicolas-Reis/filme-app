package com.grupo.catalogoFilme.dto.usuario;

public class UsuarioUpdateDTO {

	private String nome;
	private String urlImage;

	public UsuarioUpdateDTO() {}

	public UsuarioUpdateDTO(String nome, String urlImage) {
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
