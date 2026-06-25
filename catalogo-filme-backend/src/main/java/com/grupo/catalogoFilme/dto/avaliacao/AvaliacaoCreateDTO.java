package com.grupo.catalogoFilme.dto.avaliacao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AvaliacaoCreateDTO {

	@NotBlank(message = "Comentário é obrigatório")
	private String comentario;
	private String urlImage;
	@NotNull(message = "Nota é obrigatória")
	private Double nota;
	@NotNull(message = "ID do filme é obrigatório")
	private Integer filmeId;

	public AvaliacaoCreateDTO() {}

	public AvaliacaoCreateDTO(String comentario, String urlImage, Double nota, Integer filmeId) {
		this.comentario = comentario;
		this.urlImage = urlImage;
		this.nota = nota;
		this.filmeId = filmeId;
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

	public Integer getFilmeId() {
		return filmeId;
	}

	public void setFilmeId(Integer filmeId) {
		this.filmeId = filmeId;
	}
}
