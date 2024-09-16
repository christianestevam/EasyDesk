package com.ufc.easydesk.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cliente extends Usuario {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cnpjCpf;

    @Column(nullable = false)
    private String telefone;

    @OneToOne(mappedBy = "proprietario", cascade = CascadeType.ALL)
    private Restaurante restaurante;
}
