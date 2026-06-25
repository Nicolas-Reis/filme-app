package com.grupo.catalogoFilme.dto.avaliacao;

import com.grupo.catalogoFilme.dto.filme.FilmeResponseDTO;
import com.grupo.catalogoFilme.dto.usuario.UsuarioResponseDTO;
import com.grupo.catalogoFilme.enums.StatusRegistro;

public class AvaliacaoResponseDTO {

	private Integer id;
	private String comentario;
	private String urlImage;
	private Double nota;
	private StatusRegistro status;
	private UsuarioResponseDTO usuario;
	private FilmeResponseDTO filme;

	public AvaliacaoResponseDTO() {}

	public AvaliacaoResponseDTO(Integer id, String comentario, String urlImage, Double nota, StatusRegistro status,
			UsuarioResponseDTO usuario, FilmeResponseDTO filme) {
		this.id = id;
		this.comentario = comentario;
		this.urlImage = urlImage;
		this.nota = nota;
		this.status = status;
		this.usuario = usuario;
		this.filme = filme;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public Double getNota() {
		return nota;
	}

	public void setNota(Double nota) {
		this.nota = nota;
	}

	public StatusRegistro getStatus() {
		return status;
	}

	public void setStatus(StatusRegistro status) {
		this.status = status;
	}

	public UsuarioResponseDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioResponseDTO usuario) {
		this.usuario = usuario;
	}

	public FilmeResponseDTO getFilme() {
		return filme;
	}

	public void setFilme(FilmeResponseDTO filme) {
		this.filme = filme;
	}
}
