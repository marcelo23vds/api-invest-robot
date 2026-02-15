package com.mvieiradev.invest_robot.services;

import com.mvieiradev.invest_robot.infrastructure.entities.Ativo;
import com.mvieiradev.invest_robot.infrastructure.repository.AtivoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtivoService {

    private final AtivoRepository repository;

    public AtivoService(AtivoRepository repository) {
        this.repository = repository;
    }

    // Salva/atualiza um ativo (Ex: PETR4)
    public Ativo salvar(Ativo ativo) {
        // converter o ticker sempre para MAIUSCULO
        ativo.setTicker(ativo.getTicker().toUpperCase());
        return repository.save(ativo);
    }

    public List<Ativo> listarTodos() {
        return repository.findAll();
    }

    public Optional<Ativo> buscarPorTicker(String ticker) {
        return repository.findByTicker(ticker.toUpperCase());
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}