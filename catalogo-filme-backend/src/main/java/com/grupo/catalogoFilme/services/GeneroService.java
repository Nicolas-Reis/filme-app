package com.grupo.catalogoFilme.services;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo.catalogoFilme.dto.genero.GeneroResponseDTO;
import com.grupo.catalogoFilme.mapper.GeneroMapper;

@Service
public class GeneroService {
	@Autowired private GeneroMapper generoMapper;

	public List<GeneroResponseDTO> procurarGenero() {
		return generoMapper.listarTodos();
	}
}
