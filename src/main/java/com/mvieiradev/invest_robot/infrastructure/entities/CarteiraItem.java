package com.mvieiradev.invest_robot.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "tb_carteira_item")
public class CarteiraItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Permite que você envie o ID da carteira, mas evita loop no retorno
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "id_carteira")
    private Carteira carteira;

    // Aqui NÃO usamos WRITE_ONLY, pois quando listarmos os itens,
    // queremos ver os dados do Ativo (Ticker, Cotação)
    @ManyToOne
    @JoinColumn(name = "id_ativo")
    private Ativo ativo;

    private Integer quantidade;

    @Column(name = "percentual_alvo", precision = 5, scale = 2)
    private BigDecimal percentualAlvo;
}