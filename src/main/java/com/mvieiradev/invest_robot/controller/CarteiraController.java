package com.mvieiradev.invest_robot.controller;

import com.mvieiradev.invest_robot.infrastructure.entities.Carteira;
import com.mvieiradev.invest_robot.services.CarteiraService;
import com.mvieiradev.invest_robot.services.OrdemExecucao;
import com.mvieiradev.invest_robot.services.SugestaoInvestimento;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/carteiras")
public class CarteiraController {

    private final CarteiraService service;

    public CarteiraController(CarteiraService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Carteira> criar(@RequestBody Carteira carteira) {
        return ResponseEntity.ok(service.salvar(carteira));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Carteira>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carteira> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/rebalancear")
    public ResponseEntity<List<SugestaoInvestimento>> rebalancear(
            @PathVariable Long id,
            @RequestParam BigDecimal aporte) {

        return ResponseEntity.ok(service.calcularRebalanceamento(id, aporte));
    }

    @PostMapping("/{id}/operar")
    public ResponseEntity<Void> operar(
            @PathVariable Long id,
            @RequestBody List<OrdemExecucao> ordens) {

        service.realizarOperacao(id, ordens);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carteira> atualizar(@PathVariable Long id, @RequestBody Carteira carteira) {
        return ResponseEntity.ok(service.atualizar(id, carteira));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}