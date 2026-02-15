package com.mvieiradev.invest_robot.controller;

import com.mvieiradev.invest_robot.infrastructure.entities.Usuario;
import com.mvieiradev.invest_robot.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UsuarioService service;

    public UserController(UsuarioService service) {
        this.service = service;
    }

    // POST: Cria um usuário
    // URL: http://localhost:8080/users
    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        Usuario usuarioCriado = service.criarUsuario(usuario);
        // Retorna 201 Created com a URL do novo recurso
        return ResponseEntity.created(URI.create("/users/" + usuarioCriado.getId())).body(usuarioCriado);
    }

    // GET: Busca por ID
    // URL: http://localhost:8080/users/1
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        var usuario = service.buscarPorId(id);

        // Se achou, retorna 200 OK com o usuário. Se não, retorna 404 Not Found.
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET: Lista todos
    // URL: http://localhost:8080/users
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }
}