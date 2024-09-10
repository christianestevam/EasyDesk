package com.ufc.easydesk.api.http.response;

import lombok.Data;

@Data
public class EnderecoResponseDTO {
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
}
