package com.grupo.catalogoFilme.entities;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Avaliacao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Double nota;
	@Column(nullable = false)
	private String comentario;
	@Column(nullable = false)
	private LocalDate dataAvaliacao;
	
	@ManyToOne
	@JoinColumn(name  = "filme_id", nullable = false)
	private Filme filme;
	
	public Avaliacao() {}
	
	public Avaliacao(Long id, Double nota, String comentario, LocalDate dataAvaliacao, Filme filme) {
		this.id = id;
		this.nota = nota;
		this.comentario = comentario;
		this.dataAvaliacao = dataAvaliacao;
		this.filme = filme;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getNota() {
		return nota;
	}

	public void setNota(Double nota) {
		this.nota = nota;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public LocalDate getDataAvaliacao() {
		return dataAvaliacao;
	}

	public void setDataAvaliacao(LocalDate dataAvaliacao) {
		this.dataAvaliacao = dataAvaliacao;
	}

	public Filme getFilme() {
		return filme;
	}

	public void setFilme(Filme filme) {
		this.filme = filme;
	}

	
}