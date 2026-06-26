package com.grupo.catalogoFilme.enums;

public enum StatusRegistro {
	ATIVO(1),
	INATIVO(2);

	private final Integer codigo;

	StatusRegistro(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public static StatusRegistro fromCodigo(Integer codigo) {
		for (StatusRegistro status : values()) {
			if (status.codigo.equals(codigo)) {
				return status;
			}
		}
		throw new IllegalArgumentException("Código de status inválido: " + codigo);
	}
}
