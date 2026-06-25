package com.grupo.catalogoFilme.dto.plataforma;

import com.grupo.catalogoFilme.enums.StatusRegistro;

public class PlataformaResponseDTO {

	private Integer id;
	private String nome;
	private String urlImage;
	private StatusRegistro status;

	public PlataformaResponseDTO() {}

	public PlataformaResponseDTO(Integer id, String nome, String urlImage, StatusRegistro status) {
		this.id = id;
		this.nome = nome;
		this.urlImage = urlImage;
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

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public StatusRegistro getStatus() {
		return status;
	}

	public void setStatus(StatusRegistro status) {
		this.status = status;
	}
}
