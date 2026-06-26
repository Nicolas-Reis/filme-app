package com.grupo.catalogoFilme.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UsuarioCreateDTO {

	@NotBlank(message = "Nome é obrigatório")
	private String nome;
	@NotBlank(message = "E-mail é obrigatório")
	@Email(message = "E-mail inválido")
	private String email;
	@NotBlank(message = "Senha é obrigatória")
	private String senha;
	private String urlImage;

	public UsuarioCreateDTO() {}

	public UsuarioCreateDTO(String nome, String email, String senha, String urlImage) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.urlImage = urlImage;
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
}
