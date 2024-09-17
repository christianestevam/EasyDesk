package com.ufc.easydesk.api.http.request;

import com.ufc.easydesk.domain.enums.Categoria;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class ItemRequestDTO {
    private String nome;
    private String descricao;
    private Double preco;
    private Categoria categoria;
    private Boolean disponibilidade;


    public ItemRequestDTO(String pizza, String deliciosaPizza, double v, Categoria categoria, boolean b) {
    }
}
