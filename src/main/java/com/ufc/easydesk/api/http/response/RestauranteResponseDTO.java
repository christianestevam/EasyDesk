package com.ufc.easydesk.api.http.response;

import lombok.Data;

@Data
public class RestauranteResponseDTO {
    private Long id;
    private String nome;
    private String telefone;
    private EnderecoResponseDTO endereco;
    private String cnpj;
}
