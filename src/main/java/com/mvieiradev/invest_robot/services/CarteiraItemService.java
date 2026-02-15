package com.mvieiradev.invest_robot.services;

import com.mvieiradev.invest_robot.infrastructure.entities.CarteiraItem;
import com.mvieiradev.invest_robot.infrastructure.repository.CarteiraItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarteiraItemService {

    private final CarteiraItemRepository repository;

    public CarteiraItemService(CarteiraItemRepository repository) {
        this.repository = repository;
    }

    // Adiciona um ativo à carteira ou atualiza um existente
    public CarteiraItem adicionarItem(CarteiraItem item) {
        return repository.save(item);
    }

    public List<CarteiraItem> listarPorCarteira(Long carteiraId) {
        return repository.findByCarteiraId(carteiraId);
    }

    public void removerItem(Long id) {
        repository.deleteById(id);
    }
}