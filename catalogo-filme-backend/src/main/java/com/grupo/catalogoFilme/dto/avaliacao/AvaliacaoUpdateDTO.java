package com.grupo.catalogoFilme.dto.avaliacao;

public class AvaliacaoUpdateDTO {

	private String comentario;
	private String urlImage;
	private Double nota;

	public AvaliacaoUpdateDTO() {}

	public AvaliacaoUpdateDTO(String comentario, String urlImage, Double nota) {
		this.comentario = comentario;
		this.urlImage = urlImage;
		this.nota = nota;
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
}
