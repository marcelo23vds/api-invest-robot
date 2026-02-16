package com.mvieiradev.invest_robot.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class SugestaoInvestimento {
    private String ticker;              // Ex PETR4
    private Integer quantidadeAtual;    // Ex 10
    private Integer quantidadeComprar;  // Ex 5
    private BigDecimal valorGastar;     // Ex 150.00 (dinheiro)
}