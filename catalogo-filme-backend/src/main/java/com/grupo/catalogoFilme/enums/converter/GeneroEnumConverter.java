package com.grupo.catalogoFilme.enums.converter;

import com.grupo.catalogoFilme.enums.GeneroEnum;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class GeneroEnumConverter implements AttributeConverter<GeneroEnum, Integer> {

	@Override
	public Integer convertToDatabaseColumn(GeneroEnum genero) {
		return genero == null ? null : genero.getCodigo();
	}

	@Override
	public GeneroEnum convertToEntityAttribute(Integer codigo) {
		return codigo == null ? null : GeneroEnum.fromCodigo(codigo);
	}
}
