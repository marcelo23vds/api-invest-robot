package com.mvieiradev.invest_robot.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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
    private BigDecimal valorTotal;

    // relacionamento com usuario (Muitas carteiras para 1 usuário)
    // WRITE_ONLY: Significa que o campo só funciona na "escrita" (quando você manda o JSON para o Java).
    // No momento de "leitura" (quando o Java responde para o Postman), ele ignora o campo, impedindo o loop infinito.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    // relacionamento com itens (1 Carteira tem vários itens)
    @OneToMany(mappedBy = "carteira")
    private List<CarteiraItem> itens;
}
