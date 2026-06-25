package com.grupo.catalogoFilme.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.grupo.catalogoFilme.entities.Cargo;
import com.grupo.catalogoFilme.entities.Usuario;
import com.grupo.catalogoFilme.enums.StatusRegistro;
import com.grupo.catalogoFilme.repositories.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UsuarioRepository usuarioRepository;

	public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

		List<SimpleGrantedAuthority> authorities = usuario.getCargos().stream()
				.map(Cargo::getNome)
				.map(SimpleGrantedAuthority::new)
				.toList();

		return User.builder()
				.username(usuario.getEmail())
				.password(usuario.getSenha())
				.disabled(usuario.getStatus() != StatusRegistro.ATIVO)
				.authorities(authorities)
				.build();
	}
}
