package com.grupo.catalogoFilme.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.grupo.catalogoFilme.dto.usuario.UsuarioCreateDTO;
import com.grupo.catalogoFilme.dto.usuario.UsuarioResponseDTO;
import com.grupo.catalogoFilme.entities.Cargo;
import com.grupo.catalogoFilme.entities.Usuario;
import com.grupo.catalogoFilme.exceptions.RegistroNaoEncontradoException;
import com.grupo.catalogoFilme.repositories.CargoRepository;

@Component
public class UsuarioMapper {

	private final CargoRepository cargoRepository;

	public UsuarioMapper(CargoRepository cargoRepository) {
		this.cargoRepository = cargoRepository;
	}

	public UsuarioResponseDTO toDTO(Usuario usuario) {
		if (usuario == null) return null;
		Set<String> cargos = usuario.getCargos() == null ? Set.of()
				: usuario.getCargos().stream().map(Cargo::getNome).collect(Collectors.toSet());
		return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(),
				usuario.getUrlImage(), usuario.getStatus(), cargos);
	}

	public Usuario toEntity(UsuarioCreateDTO dto) {
		Usuario usuario = new Usuario();
		usuario.setNome(dto.getNome());
		usuario.setEmail(dto.getEmail());
		usuario.setSenha(dto.getSenha());
		usuario.setUrlImage(dto.getUrlImage());
		Cargo cargo = cargoRepository.findByNome(dto.getCargo().name())
				.orElseThrow(() -> new RegistroNaoEncontradoException("Cargo não encontrado: " + dto.getCargo().name()));
		usuario.getCargos().add(cargo);
		return usuario;
	}
}
