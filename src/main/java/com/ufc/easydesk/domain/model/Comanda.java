package com.ufc.easydesk.domain.model;

import com.ufc.easydesk.domain.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Item> itens;

    @Column(nullable = false)
    private Integer numeroMesa;

    private String nomeConsumidor;

    private String observacao;

    @Column(nullable = false)
    private Double total;

    private Double taxaServico;

    @Column(nullable = false)
    private Boolean ativa;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private LocalDateTime dataHoraAbertura;

    private LocalDateTime dataHoraFechamento;

    @ManyToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    private Mesa mesa;

}
