package com.mvieiradev.invest_robot.infrastructure.repository;

import com.mvieiradev.invest_robot.infrastructure.entities.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarteiraRepository extends JpaRepository<Carteira, Long> {

    // Mágica do Spring: Ele entende "Find By Usuario Id"
    // Ele vai no atributo 'usuario' da classe Carteira e pega o 'id' dele.
    // SQL gerado: SELECT * FROM tb_carteira WHERE usuario_id = ?

    List<Carteira> findByUsuarioId(Long usuarioId);
}
