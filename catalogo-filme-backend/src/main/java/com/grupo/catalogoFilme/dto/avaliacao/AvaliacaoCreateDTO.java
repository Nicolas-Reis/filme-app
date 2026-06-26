package com.grupo.catalogoFilme.dto.avaliacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AvaliacaoCreateDTO {

	@NotBlank(message = "Comentário é obrigatório")
	private String comentario;
	@NotNull(message = "Nota é obrigatória")
	private Double nota;
	@NotNull(message = "ID do filme é obrigatório")
	private Integer filmeId;

	public AvaliacaoCreateDTO() {}

	public AvaliacaoCreateDTO(String comentario, Double nota, Integer filmeId) {
		this.comentario = comentario;
		this.nota = nota;
		this.filmeId = filmeId;
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

	public Integer getFilmeId() {
		return filmeId;
	}

	public void setFilmeId(Integer filmeId) {
		this.filmeId = filmeId;
	}
}
