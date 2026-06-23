package com.grupo.catalogoFilme.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo.catalogoFilme.entities.Usuario;
import com.grupo.catalogoFilme.exceptions.DadosInvalidosException;
import com.grupo.catalogoFilme.exceptions.RegistroNaoEncontradoException;
import com.grupo.catalogoFilme.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired private UsuarioRepository repository;

    public Usuario login(String username, String senha) {
        if (username == null || username.isBlank() || senha == null || senha.isBlank()) throw new DadosInvalidosException("Usuário e senha são obrigatórios.");
        Usuario usuario = repository.findByUsername(username).orElseThrow(() -> new RegistroNaoEncontradoException("Usuário não cadastrado."));
        if (!usuario.getSenha().equals(senha)) throw new DadosInvalidosException("Senha incorreta.");
        return usuario;
    }
    
    public String cadastrar(Usuario usuario) {
        if (repository.findByUsername(usuario.getUsername()).isPresent()) throw new DadosInvalidosException("Username indisponível.");
        repository.save(usuario);
        return "Usuário cadastrado com sucesso!";
    }
}