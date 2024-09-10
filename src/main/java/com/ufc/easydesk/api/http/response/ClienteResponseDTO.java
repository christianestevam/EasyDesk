package com.ufc.easydesk.api.http.response;

import lombok.Data;

import java.util.List;

@Data
public class ClienteResponseDTO {
    private Long id;
    private String email;
    private String nome;
    private String cnpjCpf;
    private String telefone;
    private List<RestauranteResponseDTO> restaurantes; // Usando o DTO simplificado
}
