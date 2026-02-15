package com.mvieiradev.invest_robot.controller;

import com.mvieiradev.invest_robot.infrastructure.entities.CarteiraItem;
import com.mvieiradev.invest_robot.services.CarteiraItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens")
public class CarteiraItemController {

    private final CarteiraItemService service;

    public CarteiraItemController(CarteiraItemService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CarteiraItem> adicionar(@RequestBody CarteiraItem item) {
        return ResponseEntity.ok(service.adicionarItem(item));
    }

    @GetMapping("/carteira/{carteiraId}")
    public ResponseEntity<List<CarteiraItem>> listarPorCarteira(@PathVariable Long carteiraId) {
        return ResponseEntity.ok(service.listarPorCarteira(carteiraId));
    }
}