package com.ufc.easydesk.api.http.response;

import com.ufc.easydesk.domain.model.Endereco;
import lombok.Data;

@Data
public class RestauranteResponse {

    private Long id;
    private String nome;
    private String cnpj;
    private String telefone;
    private Endereco endereco;
    private String proprietarioEmail;
}
