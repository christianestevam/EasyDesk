package com.ufc.easydesk.api.http.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor  // Isso adiciona o construtor com todos os argumentos
public class ItemResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private String categoria;
    private Boolean disponibilidade;

    public ItemResponseDTO() {

    }
}
