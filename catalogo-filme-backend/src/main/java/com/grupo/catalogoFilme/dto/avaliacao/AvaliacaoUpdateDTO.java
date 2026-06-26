package com.grupo.catalogoFilme.dto.avaliacao;

public class AvaliacaoUpdateDTO {

	private String comentario;
	private Double nota;

	public AvaliacaoUpdateDTO() {}

	public AvaliacaoUpdateDTO(String comentario, Double nota) {
		this.comentario = comentario;
		this.nota = nota;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Double getNota() {
		return nota;
	}

	public void setNota(Double nota) {
		this.nota = nota;
	}
}
