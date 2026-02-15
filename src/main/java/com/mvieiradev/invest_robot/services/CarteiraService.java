package com.mvieiradev.invest_robot.services;

import com.mvieiradev.invest_robot.infrastructure.entities.Carteira;
import com.mvieiradev.invest_robot.infrastructure.repository.CarteiraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarteiraService {

    private final CarteiraRepository repository;

    public CarteiraService(CarteiraRepository repository) {
        this.repository = repository;
    }

    public Carteira salvar(Carteira carteira) {
        return repository.save(carteira);
    }

    public List<Carteira> listarPorUsuario(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    public Optional<Carteira> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}