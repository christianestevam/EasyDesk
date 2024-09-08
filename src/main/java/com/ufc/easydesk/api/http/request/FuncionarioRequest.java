package com.ufc.easydesk.api.http.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class FuncionarioRequest {

    @NotBlank(message = "O nome do funcionário é obrigatório.")
    private String nome;

    @Email
    @NotBlank(message = "O email é obrigatório.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    private String senha;

    @NotBlank(message = "A role é obrigatória.")
    private String role; // A role como STRING (ex: ROLE_FUNCIONARIO)

    private Long restauranteId; // Restaurante ao qual o funcionário será associado
}
