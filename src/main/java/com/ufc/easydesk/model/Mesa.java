package com.ufc.easydesk.model;

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
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int numeroMesa;

    @Column(nullable = false)
    private Boolean disponibilidade;

    @ManyToOne
    @JoinColumn(name = "garcom_id", nullable = false)
    private Funcionario garcom;

    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL)
    private List<Comanda> comandas;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

}
