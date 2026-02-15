package com.mvieiradev.invest_robot.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_carteira_item")
public class CarteiraItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- LIGAÇÃO COM A CARTEIRA (PAI) ---
    @ManyToOne
    @JoinColumn(name = "id_carteira") // <--- Tem que bater com o SQL
    @JsonIgnore // <--- OBRIGATÓRIO!
    // Se não colocar isso, quando você listar os itens, ele tenta mostrar a carteira,
    // que mostra os itens, que mostra a carteira... (Loop Infinito)
    private Carteira carteira;

    // --- LIGAÇÃO COM O ATIVO (A AÇÃO EM SI) ---
    @ManyToOne
    @JoinColumn(name = "id_ativo") // <--- Tem que bater com o SQL
    // AQUI NÃO TEM @JsonIgnore!
    // Motivo: Quando eu consulto o item, eu QUERO saber qual é a ação (PETR4, VALE3).
    // O Ativo não tem lista de itens, então não gera loop.
    private Ativo ativo;

    private Integer quantidade;

    @Column(name = "percentual_alvo") // <--- Mapeando percentual_alvo
    private BigDecimal percentualAlvo;
}