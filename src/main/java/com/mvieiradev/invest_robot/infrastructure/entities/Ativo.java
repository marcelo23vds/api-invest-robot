package com.mvieiradev.invest_robot.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_ativo")
public class Ativo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // O ticker é único (ex: PETR4). Se tentar salvar outro igual, dá erro.
    @Column(unique = true, nullable = false, length = 10)
    private String ticker;

    // Mapeando o DECIMAL(10, 2) do banco
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cotacao;

    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

}
