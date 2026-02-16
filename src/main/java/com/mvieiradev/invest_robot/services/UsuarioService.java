package com.mvieiradev.invest_robot.services;

import com.mvieiradev.invest_robot.infrastructure.entities.Usuario;
import com.mvieiradev.invest_robot.infrastructure.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    // CRIAR
    public Usuario criarUsuario(Usuario usuario) {
        return repository.save(usuario);
    }

    // LISTAR TODOS
    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    // BUSCAR POR ID (Auxiliar para o update)
    public Optional<Usuario> buscarPorId(Long id) {
        return repository.findById(id);
    }

    // ATUALIZAR
    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        return repository.findById(id).map(usuario -> {
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setEmail(usuarioAtualizado.getEmail());
            return repository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    // DELETAR
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado!");
        }
        repository.deleteById(id);
    }
}