package com.ufc.easydesk.api.http.request;

import lombok.Data;

import java.util.List;

@Data
public class CardapioRequestDTO {
    private Long restauranteId; // O restaurante ao qual o cardápio pertence
    private List<ItemRequestDTO> itens;
}
