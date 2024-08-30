package com.ufc.easydesk.model;

import com.ufc.easydesk.model.enums.Status;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Comanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> itens;

    @Column(nullable = false)
    private int numeroMesa;

    private String nomeConsumidor;

    private String observacao;

    @Column(nullable = false)
    private double total;

    private double taxaServico;

    @Column(nullable = false)
    private boolean ativa;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private LocalDateTime dataHoraAbertura;

    private LocalDateTime dataHoraFechamento;

    @ManyToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    private Mesa mesa;

}
