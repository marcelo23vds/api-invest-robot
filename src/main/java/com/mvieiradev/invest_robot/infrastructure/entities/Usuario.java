package com.mvieiradev.invest_robot.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

//uma especie de cracha, ou identificação de que esta nao é uma classe comum, e sim uma tabela do BD
@Entity

@Getter
@Setter

//sobrecarga de construtores
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "tb_usuario")

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;

    // mappedBy = "usuario" refere-se ao atributo 'usuario' na classe Carteira
    @OneToMany(mappedBy = "usuario")
    private List<Carteira> carteiras;
}