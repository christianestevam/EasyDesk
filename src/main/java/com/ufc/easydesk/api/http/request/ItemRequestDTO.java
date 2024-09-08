package com.ufc.easydesk.api.http.request;

import com.ufc.easydesk.model.enums.Categoria;
import lombok.Data;

@Data
public class ItemRequestDTO {
    private String nome;
    private String descricao;
    private Double preco;
    private Categoria categoria;
    private Boolean disponibilidade;
}
