package com.grupo.catalogoFilme.entities;

import java.util.HashSet;
import java.util.Set;

import com.grupo.catalogoFilme.enums.StatusRegistro;
import com.grupo.catalogoFilme.enums.converter.StatusRegistroConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "senha", nullable = false)
	private String senha;

	@Column(name = "url_image")
	private String urlImage;

	@Convert(converter = StatusRegistroConverter.class)
	@Column(name = "status", nullable = false)
	private StatusRegistro status = StatusRegistro.ATIVO;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_cargo",
		joinColumns = @JoinColumn(name = "usuario_id"),
		inverseJoinColumns = @JoinColumn(name = "cargo_id"))
	private Set<Cargo> cargos = new HashSet<>();

	public Usuario() {}

	public Usuario(Integer id, String nome, String email, String senha, String urlImage) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.urlImage = urlImage;
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

	public StatusRegistro getStatus() {
		return status;
	}

	public void setStatus(StatusRegistro status) {
		this.status = status;
	}

	public Set<Cargo> getCargos() {
		return cargos;
	}

	public void setCargos(Set<Cargo> cargos) {
		this.cargos = cargos;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Usuario usuario = (Usuario) o;
		return id != null && id.equals(usuario.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
