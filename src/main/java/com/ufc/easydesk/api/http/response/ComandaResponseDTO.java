package com.ufc.easydesk.api.http.response;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ComandaResponseDTO {
    private Long id;
    private Integer numeroMesa;
    private String nomeConsumidor;
    private List<ItemResponseDTO> itens;
    private Double total;
    private Double taxaServico;
    private Boolean ativa;
    private String status;
    private LocalDateTime dataHoraAbertura;
    private LocalDateTime dataHoraFechamento;
}
