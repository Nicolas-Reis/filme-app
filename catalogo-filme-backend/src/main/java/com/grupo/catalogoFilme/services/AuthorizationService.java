package com.grupo.catalogoFilme.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.grupo.catalogoFilme.dto.auth.LoginResponseDTO;
import com.grupo.catalogoFilme.dto.usuario.UsuarioLoginDTO;
import com.grupo.catalogoFilme.entities.Usuario;
import com.grupo.catalogoFilme.enums.CargoEnum;
import com.grupo.catalogoFilme.enums.StatusRegistro;
import com.grupo.catalogoFilme.exceptions.RegistroNaoEncontradoException;
import com.grupo.catalogoFilme.mapper.UsuarioMapper;
import com.grupo.catalogoFilme.repositories.UsuarioRepository;
import com.grupo.catalogoFilme.security.JwtUtil;

@Service
public class AuthorizationService {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final UsuarioRepository usuarioRepository;
	private final UsuarioMapper usuarioMapper;

	public AuthorizationService(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
			UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.usuarioRepository = usuarioRepository;
		this.usuarioMapper = usuarioMapper;
	}

	public LoginResponseDTO autenticar(UsuarioLoginDTO dto) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha()));

		Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
				.orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não cadastrado."));
		garantirAtivo(usuario);

		String token = jwtUtil.gerarToken(usuario);
		return new LoginResponseDTO(token, "Bearer", usuarioMapper.toDTO(usuario));
	}

	public Usuario usuarioAutenticado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			throw new RegistroNaoEncontradoException("Usuário não autenticado.");
		}
		Usuario usuario = usuarioRepository.findByEmail(authentication.getName())
				.orElseThrow(() -> new RegistroNaoEncontradoException("Usuário autenticado não encontrado."));
		garantirAtivo(usuario);
		return usuario;
	}

	public boolean isAdmin(Usuario usuario) {
		return usuario.getCargos().stream()
				.anyMatch(cargo -> CargoEnum.ROLE_ADMIN.name().equals(cargo.getNome()));
	}

	private void garantirAtivo(Usuario usuario) {
		if (usuario.getStatus() != StatusRegistro.ATIVO) {
			throw new RegistroNaoEncontradoException("Usuário inativo.");
		}
	}
}
