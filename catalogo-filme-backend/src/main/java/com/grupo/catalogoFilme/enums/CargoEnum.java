package com.grupo.catalogoFilme.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CargoEnum {
	ROLE_ADMIN(1),
	ROLE_USER(2);

	private final Integer codigo;

	CargoEnum(Integer codigo) {
		this.codigo = codigo;
	}

	@JsonValue
	public Integer getCodigo() {
		return codigo;
	}

	@JsonCreator
	public static CargoEnum fromCodigo(Integer codigo) {
		for (CargoEnum cargo : values()) {
			if (cargo.codigo.equals(codigo)) {
				return cargo;
			}
		}
		throw new IllegalArgumentException("Código de cargo inválido: " + codigo);
	}
}
