package com.grupo.catalogoFilme.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum GeneroEnum {
	ACAO(1, "Ação"),
	AVENTURA(2, "Aventura"),
	COMEDIA(3, "Comédia"),
	DRAMA(4, "Drama"),
	TERROR(5, "Terror"),
	FICCAO_CIENTIFICA(6, "Ficção Científica"),
	ROMANCE(7, "Romance"),
	SUSPENSE(8, "Suspense"),
	ANIMACAO(9, "Animação"),
	DOCUMENTARIO(10, "Documentário"),
	FANTASIA(11, "Fantasia"),
	MUSICAL(12, "Musical");

	private final Integer codigo;
	private final String nome;

	GeneroEnum(Integer codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}

	@JsonValue
	public Integer getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	@JsonCreator
	public static GeneroEnum fromCodigo(Integer codigo) {
		for (GeneroEnum genero : values()) {
			if (genero.codigo.equals(codigo)) {
				return genero;
			}
		}
		throw new IllegalArgumentException("Código de gênero inválido: " + codigo);
	}
}
