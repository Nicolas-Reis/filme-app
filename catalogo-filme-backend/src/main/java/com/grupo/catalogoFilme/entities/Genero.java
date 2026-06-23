package com.grupo.catalogoFilme.entities;



import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Genero {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String tpGenero;
	@JsonBackReference
	@OneToMany(mappedBy = "genero")
	private List<Filme> filmes;
	
	public Genero() {}
	
	public Genero(Long id, String tpGenero) {
		this.id = id;
		this.tpGenero = tpGenero;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTpGenero() {
		return tpGenero;
	}

	public void setTpGenero(String tpGenero) {
		this.tpGenero = tpGenero;
	}

	public List<Filme> getFilmes() {
		return filmes;
	}

	public void setFilmes(List<Filme> filmes) {
		this.filmes = filmes;
	}

	
}