package com.ufc.easydesk.api.http.request;

import com.ufc.easydesk.model.Endereco;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RestauranteRequest {

    @NotBlank(message = "O nome do restaurante é obrigatório.")
    private String nome;

    @NotBlank(message = "O CNPJ é obrigatório.")
    private String cnpj;

    @NotBlank(message = "O telefone é obrigatório.")
    private String telefone;


    private Endereco endereco;
}
