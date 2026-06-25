package com.grupo.catalogoFilme.services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo.catalogoFilme.entities.Usuario;
import com.grupo.catalogoFilme.enums.StatusRegistro;
import com.grupo.catalogoFilme.exceptions.DadosInvalidosException;
import com.grupo.catalogoFilme.exceptions.RegistroNaoEncontradoException;
import com.grupo.catalogoFilme.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired private UsuarioRepository repository;

    public List<Usuario> listar() { return repository.findAllByStatusNot(StatusRegistro.INATIVO); }

    public List<Usuario> findAll() { return repository.findAll(); }

    public Usuario buscarPorId(Integer id) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não encontrado."));
        if (usuario.getStatus() == StatusRegistro.INATIVO) throw new RegistroNaoEncontradoException("Usuário não encontrado.");
        return usuario;
    }

    public Usuario login(String email, String senha) {
        if (email == null || email.isBlank() || senha == null || senha.isBlank()) throw new DadosInvalidosException("E-mail e senha são obrigatórios.");
        Usuario usuario = repository.findByEmail(email).orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não cadastrado."));
        if (!usuario.getSenha().equals(senha)) throw new DadosInvalidosException("Senha incorreta.");
        return usuario;
    }

    public Usuario cadastrar(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().isBlank()) throw new DadosInvalidosException("Nome é obrigatório.");
        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) throw new DadosInvalidosException("E-mail é obrigatório.");
        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) throw new DadosInvalidosException("Senha é obrigatória.");
        if (repository.findByEmail(usuario.getEmail()).isPresent()) throw new DadosInvalidosException("E-mail indisponível.");
        usuario.setStatus(StatusRegistro.ATIVO);
        return repository.save(usuario);
    }
}