package com.grupo.catalogoFilme.dto.usuario;

import java.util.Set;

import com.grupo.catalogoFilme.enums.StatusRegistro;

public class UsuarioResponseDTO {

	private Integer id;
	private String nome;
	private String email;
	private String urlImage;
	private StatusRegistro status;
	private Set<String> cargos;

	public UsuarioResponseDTO() {}

	public UsuarioResponseDTO(Integer id, String nome, String email, String urlImage, StatusRegistro status, Set<String> cargos) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.urlImage = urlImage;
		this.status = status;
		this.cargos = cargos;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Set<String> getCargos() {
		return cargos;
	}

	public void setCargos(Set<String> cargos) {
		this.cargos = cargos;
	}
}
