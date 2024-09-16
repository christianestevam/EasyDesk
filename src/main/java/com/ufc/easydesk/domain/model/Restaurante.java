package com.ufc.easydesk.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Embedded
    private Endereco endereco;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false, unique = true)
    private String cnpj;

    @OneToOne
    @JoinColumn(name = "proprietario_id", nullable = false)
    private Cliente proprietario;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL)
    private List<Funcionario> funcionarios;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL)
    private List<Mesa> mesas;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL)
    private List<Cardapio> cardapios;
}
