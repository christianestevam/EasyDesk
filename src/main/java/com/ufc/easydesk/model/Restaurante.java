package com.ufc.easydesk.model;

import jakarta.persistence.*;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "proprietario_id", nullable = false)
    private Cliente proprietario;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL)
    private List<Funcionario> funcionarios;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL)
    private List<Mesa> mesas;

    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL)
    private List<Cardapio> cardapios;

}
