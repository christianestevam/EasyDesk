package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.response.ClienteResponseDTO;
import com.ufc.easydesk.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Clientes", description = "Gerenciamento de Clientes")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(summary = "Obter dados do cliente logado", description = "Retorna os dados do cliente logado, incluindo o restaurante.")
    @ApiResponse(responseCode = "200", description = "Dados do cliente retornados com sucesso")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<ClienteResponseDTO> getClienteLogado() {
        ClienteResponseDTO cliente = clienteService.getClienteLogado();
        return ResponseEntity.ok(cliente);
    }
}
