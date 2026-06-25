package com.grupo.catalogoFilme.services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.grupo.catalogoFilme.dto.usuario.UsuarioCreateDTO;
import com.grupo.catalogoFilme.dto.usuario.UsuarioResponseDTO;
import com.grupo.catalogoFilme.entities.Cargo;
import com.grupo.catalogoFilme.entities.Usuario;
import com.grupo.catalogoFilme.enums.CargoEnum;
import com.grupo.catalogoFilme.enums.StatusRegistro;
import com.grupo.catalogoFilme.exceptions.DadosInvalidosException;
import com.grupo.catalogoFilme.exceptions.RegistroNaoEncontradoException;
import com.grupo.catalogoFilme.mapper.UsuarioMapper;
import com.grupo.catalogoFilme.repositories.CargoRepository;
import com.grupo.catalogoFilme.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired private UsuarioRepository repository;
    @Autowired private CargoRepository cargoRepository;
    @Autowired private UsuarioMapper usuarioMapper;
    @Autowired private PasswordEncoder passwordEncoder;

    public List<UsuarioResponseDTO> listar() {
    	return repository.findAllByStatusNot(StatusRegistro.INATIVO).stream().map(usuarioMapper::toDTO).toList();
    }

    public List<UsuarioResponseDTO> findAll() {
    	return repository.findAll().stream().map(usuarioMapper::toDTO).toList();
    }

    public UsuarioResponseDTO buscarPorId(Integer id) {
        return usuarioMapper.toDTO(buscarAtivo(id));
    }

    public UsuarioResponseDTO cadastrar(UsuarioCreateDTO dto) {
        if (repository.findByEmail(dto.getEmail()).isPresent()) throw new DadosInvalidosException("E-mail indisponível.");
        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setStatus(StatusRegistro.ATIVO);
        return usuarioMapper.toDTO(repository.save(usuario));
    }

    public UsuarioResponseDTO promoverParaAdmin(Integer id) {
        Usuario usuario = buscarAtivo(id);
        Cargo cargoAdmin = cargoRepository.findByNome(CargoEnum.ROLE_ADMIN.name())
                .orElseThrow(() -> new RegistroNaoEncontradoException("Cargo não encontrado: " + CargoEnum.ROLE_ADMIN.name()));
        usuario.getCargos().clear();
        usuario.getCargos().add(cargoAdmin);
        return usuarioMapper.toDTO(repository.save(usuario));
    }

    private Usuario buscarAtivo(Integer id) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não encontrado."));
        if (usuario.getStatus() == StatusRegistro.INATIVO) throw new RegistroNaoEncontradoException("Usuário não encontrado.");
        return usuario;
    }
}
