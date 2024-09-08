package com.ufc.easydesk.api.http.request;

import lombok.Data;

@Data
public class MesaRequest {

    private Long numeroMesa;
    private Boolean disponibilidade;
    private Long restauranteId;
}
