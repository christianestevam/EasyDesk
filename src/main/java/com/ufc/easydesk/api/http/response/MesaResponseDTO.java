package com.ufc.easydesk.api.http.response;

import lombok.Data;

@Data
public class MesaResponseDTO {
    private Long id;             // ID da mesa
    private Long numeroMesa;
    private Boolean disponibilidade;
}
