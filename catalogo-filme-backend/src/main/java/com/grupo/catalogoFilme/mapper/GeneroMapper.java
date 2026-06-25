package com.grupo.catalogoFilme.mapper;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.grupo.catalogoFilme.dto.genero.GeneroResponseDTO;
import com.grupo.catalogoFilme.enums.GeneroEnum;

@Component
public class GeneroMapper {

	public GeneroResponseDTO toDTO(GeneroEnum genero) {
		if (genero == null) return null;
		return new GeneroResponseDTO(genero.getCodigo(), genero.getNome());
	}

	public List<GeneroResponseDTO> listarTodos() {
		return Arrays.stream(GeneroEnum.values()).map(this::toDTO).toList();
	}
}
