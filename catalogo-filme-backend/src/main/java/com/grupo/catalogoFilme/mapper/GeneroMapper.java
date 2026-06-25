package com.grupo.catalogoFilme.mapper;

import org.springframework.stereotype.Component;

import com.grupo.catalogoFilme.dto.genero.GeneroCreateDTO;
import com.grupo.catalogoFilme.dto.genero.GeneroResponseDTO;
import com.grupo.catalogoFilme.entities.Genero;

@Component
public class GeneroMapper {

	public GeneroResponseDTO toDTO(Genero genero) {
		if (genero == null) return null;
		return new GeneroResponseDTO(genero.getId(), genero.getNome(), genero.getStatus());
	}

	public Genero toEntity(GeneroCreateDTO dto) {
		Genero genero = new Genero();
		genero.setNome(dto.getNome());
		return genero;
	}
}
