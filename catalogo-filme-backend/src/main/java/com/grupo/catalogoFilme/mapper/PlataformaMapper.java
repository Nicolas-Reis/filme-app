package com.grupo.catalogoFilme.mapper;

import org.springframework.stereotype.Component;

import com.grupo.catalogoFilme.dto.plataforma.PlataformaCreateDTO;
import com.grupo.catalogoFilme.dto.plataforma.PlataformaResponseDTO;
import com.grupo.catalogoFilme.entities.Plataforma;

@Component
public class PlataformaMapper {

	public PlataformaResponseDTO toDTO(Plataforma plataforma) {
		if (plataforma == null) return null;
		return new PlataformaResponseDTO(plataforma.getId(), plataforma.getNome(), plataforma.getUrlImage(), plataforma.getStatus());
	}

	public Plataforma toEntity(PlataformaCreateDTO dto) {
		Plataforma plataforma = new Plataforma();
		plataforma.setNome(dto.getNome());
		plataforma.setUrlImage(dto.getUrlImage());
		return plataforma;
	}
}
