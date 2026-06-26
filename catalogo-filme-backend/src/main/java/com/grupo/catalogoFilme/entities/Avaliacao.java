package com.grupo.catalogoFilme.entities;

import com.grupo.catalogoFilme.enums.StatusRegistro;
import com.grupo.catalogoFilme.enums.converter.StatusRegistroConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "avaliacoes")
public class Avaliacao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "comentario", nullable = false)
	private String comentario;

	@Column(name = "nota", nullable = false)
	private Double nota;

	@Convert(converter = StatusRegistroConverter.class)
	@Column(name = "status", nullable = false)
	private StatusRegistro status = StatusRegistro.ATIVO;

	@ManyToOne
	@JoinColumn(name  = "usuario_id", nullable = false)
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name  = "filme_id", nullable = false)
	private Filme filme;

	public Avaliacao() {}

	public Avaliacao(Integer id, String comentario, Double nota, Usuario usuario, Filme filme) {
		this.id = id;
		this.comentario = comentario;
		this.nota = nota;
		this.usuario = usuario;
		this.filme = filme;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Filme getFilme() {
		return filme;
	}

	public void setFilme(Filme filme) {
		this.filme = filme;
	}

	public StatusRegistro getStatus() {
		return status;
	}

	public void setStatus(StatusRegistro status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Avaliacao avaliacao = (Avaliacao) o;
		return id != null && id.equals(avaliacao.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}

}