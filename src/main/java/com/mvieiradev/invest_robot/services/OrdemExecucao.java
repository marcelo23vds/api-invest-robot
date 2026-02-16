package com.mvieiradev.invest_robot.services;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrdemExecucao {
    private String ticker;      // Qual ativo comprar?
    private Integer quantidade; // Quantas unidades?
}