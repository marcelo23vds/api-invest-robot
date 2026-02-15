package com.mvieiradev.invest_robot.services;

import com.mvieiradev.invest_robot.infrastructure.entities.Usuario;
import com.mvieiradev.invest_robot.infrastructure.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    // Construtor para injeção de dependência
    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    // Criar um usuário
    public Usuario criarUsuario(Usuario usuario) {
        return repository.save(usuario);
    }

    // Buscar por ID
    public Optional<Usuario> buscarPorId(Long id) {
        return repository.findById(id);
    }

    // Listar todos
    public List<Usuario> listarTodos() {
        return repository.findAll();
    }
}