package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.response.ClienteResponseDTO;
import com.ufc.easydesk.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    // Endpoint para retornar as informações do cliente logado
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<ClienteResponseDTO> getClienteLogado() {
        ClienteResponseDTO cliente = clienteService.getClienteLogado();
        return ResponseEntity.ok(cliente);
    }
}
