package com.mvieiradev.invest_robot.infrastructure.repository;

import com.mvieiradev.invest_robot.infrastructure.entities.CarteiraItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarteiraItemRepository extends JpaRepository<CarteiraItem, Long> {

    // Busca todos os itens de uma carteira específica
    // SQL gerado: SELECT * FROM tb_carteira_item WHERE carteira_id = ?

    List<CarteiraItem> findByCarteiraId(Long carteiraId);
}