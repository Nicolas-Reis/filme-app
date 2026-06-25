package com.grupo.catalogoFilme.enums.converter;

import com.grupo.catalogoFilme.enums.StatusRegistro;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StatusRegistroConverter implements AttributeConverter<StatusRegistro, Integer> {

	@Override
	public Integer convertToDatabaseColumn(StatusRegistro status) {
		return status == null ? null : status.getCodigo();
	}

	@Override
	public StatusRegistro convertToEntityAttribute(Integer codigo) {
		return codigo == null ? null : StatusRegistro.fromCodigo(codigo);
	}
}
