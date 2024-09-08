package com.ufc.easydesk.api.http.response;

import lombok.Data;
import java.util.List;

@Data
public class CardapioResponseDTO {
    private Long id;
    private List<ItemResponseDTO> itens;
}
