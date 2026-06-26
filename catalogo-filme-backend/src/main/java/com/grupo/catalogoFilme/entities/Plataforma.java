package com.grupo.catalogoFilme.entities;


import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.grupo.catalogoFilme.enums.StatusRegistro;
import com.grupo.catalogoFilme.enums.converter.StatusRegistroConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "plataformas")
public class Plataforma {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "url_image")
	private String urlImage;

	@Convert(converter = StatusRegistroConverter.class)
	@Column(name = "status", nullable = false)
	private StatusRegistro status = StatusRegistro.ATIVO;

	@JsonBackReference
	@OneToMany(mappedBy = "plataforma")
	private List<Filme> filmes;

	public Plataforma() {}

	public Plataforma(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
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

	public List<Filme> getFilmes() {
		return filmes;
	}

	public void setFilmes(List<Filme> filmes) {
		this.filmes = filmes;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Plataforma plataforma = (Plataforma) o;
		return id != null && id.equals(plataforma.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

}