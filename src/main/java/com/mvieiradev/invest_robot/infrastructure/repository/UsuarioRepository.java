package com.mvieiradev.invest_robot.infrastructure.repository;

import com.mvieiradev.invest_robot.infrastructure.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
