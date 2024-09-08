package com.ufc.easydesk.api.http.request;

import lombok.Data;

import java.util.List;

@Data
public class ComandaRequestDTO {
    private Long mesaId;
    private String nomeConsumidor;
    private List<Long> itens; // IDs dos itens
    private Double taxaServico;
    private String observacao;
}
