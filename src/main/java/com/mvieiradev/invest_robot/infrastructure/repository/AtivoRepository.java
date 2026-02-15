package com.mvieiradev.invest_robot.infrastructure.repository;

import com.mvieiradev.invest_robot.infrastructure.entities.Ativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtivoRepository extends JpaRepository<Ativo, Long> {

    // Busca o ativo pelo nome (Ex: findByTicker("PETR4"))
    // Usamos Optional porque o ativo pode não existir no banco.

    Optional<Ativo> findByTicker(String ticker);
}