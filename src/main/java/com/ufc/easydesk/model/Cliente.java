package com.ufc.easydesk.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Cliente extends Usuario {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cnpjCpf;

    @Column(nullable = false)
    private String telefone;

    @OneToMany(mappedBy = "proprietario", cascade = CascadeType.ALL)
    private List<Restaurante> restaurantes;

}