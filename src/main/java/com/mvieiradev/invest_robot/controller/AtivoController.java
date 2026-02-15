package com.mvieiradev.invest_robot.controller;

import com.mvieiradev.invest_robot.infrastructure.entities.Ativo;
import com.mvieiradev.invest_robot.services.AtivoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ativos")
public class AtivoController {

    private final AtivoService service;

    public AtivoController(AtivoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Ativo> criar(@RequestBody Ativo ativo) {
        return ResponseEntity.ok(service.salvar(ativo));
    }

    @GetMapping
    public ResponseEntity<List<Ativo>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{ticker}")
    public ResponseEntity<Ativo> buscar(@PathVariable String ticker) {
        return service.buscarPorTicker(ticker)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}