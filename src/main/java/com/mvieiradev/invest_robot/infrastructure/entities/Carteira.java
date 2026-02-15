package com.mvieiradev.invest_robot.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "tb_carteira")

public class Carteira {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @Column(name = "valor_total")
    private Double valorTotal;

    // --- RELACIONAMENTO COM USUÁRIO (Muitas carteiras para 1 usuário) ---
    @ManyToOne
    @JoinColumn(name = "id_usuario") // <--- Tem que ser igual ao SQL
    @JsonIgnore // <--- OBRIGATÓRIO: Impede o Loop Infinito (Carteira -> Usuario -> Carteira...)
    private Usuario usuario;

    // --- RELACIONAMENTO COM ITENS (1 Carteira tem vários itens) ---
    @OneToMany(mappedBy = "carteira")
    private List<CarteiraItem> itens;
}
