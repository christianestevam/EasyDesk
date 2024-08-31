package com.ufc.easydesk.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Funcionario extends Usuario {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String role;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

}
