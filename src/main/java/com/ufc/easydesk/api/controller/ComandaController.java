package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.request.ComandaRequestDTO;
import com.ufc.easydesk.api.http.request.ComandaStatusRequestDTO;
import com.ufc.easydesk.api.http.response.ComandaResponseDTO;
import com.ufc.easydesk.service.ComandaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comanda")
@RequiredArgsConstructor
public class ComandaController {

    private final ComandaService comandaService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('GARCOM')")
    public ResponseEntity<ComandaResponseDTO> createComanda(@RequestBody ComandaRequestDTO request) {
        ComandaResponseDTO createdComanda = comandaService.createComanda(request);
        return new ResponseEntity<>(createdComanda, HttpStatus.CREATED);
    }

    @PutMapping("/{comandaId}/fechar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('GARCOM')")
    public ResponseEntity<ComandaResponseDTO> fecharComanda(@PathVariable Long comandaId) {
        ComandaResponseDTO comandaFechada = comandaService.fecharComanda(comandaId);
        return new ResponseEntity<>(comandaFechada, HttpStatus.OK);
    }

    @PutMapping("/{comandaId}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('GARCOM')")
    public ResponseEntity<ComandaResponseDTO> atualizarStatusComanda(
            @PathVariable Long comandaId, @RequestBody ComandaStatusRequestDTO request) {
        ComandaResponseDTO updatedComanda = comandaService.atualizarStatus(comandaId, request);
        return new ResponseEntity<>(updatedComanda, HttpStatus.OK);
    }

    @GetMapping("/{comandaId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('GARCOM')")
    public ResponseEntity<ComandaResponseDTO> getComandaById(@PathVariable Long comandaId) {
        ComandaResponseDTO comanda = comandaService.getComandaById(comandaId);
        return new ResponseEntity<>(comanda, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('GARCOM')")
    public ResponseEntity<List<ComandaResponseDTO>> getAllComandas() {
        List<ComandaResponseDTO> comandas = comandaService.getAllComandas();
        return new ResponseEntity<>(comandas, HttpStatus.OK);
    }

}
