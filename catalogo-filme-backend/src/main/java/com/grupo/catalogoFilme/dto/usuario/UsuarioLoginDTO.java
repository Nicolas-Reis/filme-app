package com.grupo.catalogoFilme.dto.usuario;

import jakarta.validation.constraints.NotBlank;

public class UsuarioLoginDTO {

	@NotBlank(message = "E-mail é obrigatório")
	private String email;
	@NotBlank(message = "Senha é obrigatória")
	private String senha;

	public UsuarioLoginDTO() {}

	public UsuarioLoginDTO(String email, String senha) {
		this.email = email;
		this.senha = senha;
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
}
