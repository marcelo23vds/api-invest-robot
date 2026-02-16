package com.mvieiradev.invest_robot.services;

import com.mvieiradev.invest_robot.infrastructure.entities.Ativo;
import com.mvieiradev.invest_robot.infrastructure.entities.Carteira;
import com.mvieiradev.invest_robot.infrastructure.entities.CarteiraItem;
import com.mvieiradev.invest_robot.infrastructure.repository.AtivoRepository;
import com.mvieiradev.invest_robot.infrastructure.repository.CarteiraItemRepository;
import com.mvieiradev.invest_robot.infrastructure.repository.CarteiraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarteiraItemService {

    private final CarteiraItemRepository repository;
    private final AtivoRepository ativoRepository;
    private final CarteiraRepository carteiraRepository;

    //injetando os repositórios extras no construtor
    public CarteiraItemService(CarteiraItemRepository repository,
                               AtivoRepository ativoRepository,
                               CarteiraRepository carteiraRepository) {
        this.repository = repository;
        this.ativoRepository = ativoRepository;
        this.carteiraRepository = carteiraRepository;
    }

    public CarteiraItem adicionarItem(CarteiraItem item) {
        // buscando o Ativo completo no banco pelo ID que veio no JSON
        Ativo ativoCompleto = ativoRepository.findById(item.getAtivo().getId())
                .orElseThrow(() -> new RuntimeException("Ativo não encontrado"));

        //buscando a Carteira completa
        Carteira carteiraCompleta = carteiraRepository.findById(item.getCarteira().getId())
                .orElseThrow(() -> new RuntimeException("Carteira não encontrada"));

        //preenchendo o item com os dados completos
        item.setAtivo(ativoCompleto);
        item.setCarteira(carteiraCompleta);

        // o retorno terá o ticker e cotação preenchido
        return repository.save(item);
    }

    public List<CarteiraItem> listarPorCarteira(Long carteiraId) {
        return repository.findByCarteiraId(carteiraId);
    }

    public CarteiraItem atualizar(Long id, CarteiraItem itemAtualizado) {
        return repository.findById(id)
                .map(itemExistente -> {
                    // Só atualiza se o valor vier no JSON (evita nulos)
                    if (itemAtualizado.getPercentualAlvo() != null) {
                        itemExistente.setPercentualAlvo(itemAtualizado.getPercentualAlvo());
                    }

                    if (itemAtualizado.getQuantidade() != null) {
                        itemExistente.setQuantidade(itemAtualizado.getQuantidade());
                    }

                    return repository.save(itemExistente);
                })
                .orElseThrow(() -> new RuntimeException("Item de carteira não encontrado!"));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Item de carteira não encontrado para exclusão!");
        }
        repository.deleteById(id);
    }
}