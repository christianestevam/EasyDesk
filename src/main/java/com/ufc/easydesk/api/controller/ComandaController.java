package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.request.ComandaRequestDTO;
import com.ufc.easydesk.api.http.request.ComandaStatusRequestDTO;
import com.ufc.easydesk.api.http.response.ComandaResponseDTO;
import com.ufc.easydesk.service.ComandaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comandas", description = "Gerenciamento de Comandas")
@RestController
@RequestMapping("/api/comanda")
@RequiredArgsConstructor
public class ComandaController {

    private final ComandaService comandaService;

    @Operation(summary = "Criar Comanda", description = "Cria uma nova comanda para a mesa especificada.")
    @ApiResponse(responseCode = "201", description = "Comanda criada com sucesso.")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('GARCOM')")
    public ResponseEntity<ComandaResponseDTO> createComanda(@RequestBody ComandaRequestDTO request) {
        ComandaResponseDTO createdComanda = comandaService.createComanda(request);
        return new ResponseEntity<>(createdComanda, HttpStatus.CREATED);
    }

    @Operation(summary = "Fechar Comanda", description = "Fecha uma comanda especificada por seu ID.")
    @ApiResponse(responseCode = "200", description = "Comanda fechada com sucesso.")
    @PutMapping("/{comandaId}/fechar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('GARCOM')")
    public ResponseEntity<ComandaResponseDTO> fecharComanda(@PathVariable Long comandaId) {
        ComandaResponseDTO comandaFechada = comandaService.fecharComanda(comandaId);
        return new ResponseEntity<>(comandaFechada, HttpStatus.OK);
    }

    @Operation(summary = "Atualizar Status da Comanda", description = "Atualiza o status de uma comanda.")
    @ApiResponse(responseCode = "200", description = "Status da comanda atualizado com sucesso.")
    @PutMapping("/{comandaId}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('GARCOM')")
    public ResponseEntity<ComandaResponseDTO> atualizarStatusComanda(
            @PathVariable Long comandaId, @RequestBody ComandaStatusRequestDTO request) {
        ComandaResponseDTO updatedComanda = comandaService.atualizarStatus(comandaId, request);
        return new ResponseEntity<>(updatedComanda, HttpStatus.OK);
    }

    @Operation(summary = "Obter Comanda por ID", description = "Retorna os detalhes de uma comanda pelo seu ID.")
    @ApiResponse(responseCode = "200", description = "Comanda retornada com sucesso.")
    @GetMapping("/{comandaId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('GARCOM')")
    public ResponseEntity<ComandaResponseDTO> getComandaById(@PathVariable Long comandaId) {
        ComandaResponseDTO comanda = comandaService.getComandaById(comandaId);
        return new ResponseEntity<>(comanda, HttpStatus.OK);
    }

    @Operation(summary = "Listar Todas as Comandas", description = "Retorna a lista de todas as comandas registradas.")
    @ApiResponse(responseCode = "200", description = "Comandas listadas com sucesso.")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE') or hasRole('GARCOM')")
    public ResponseEntity<List<ComandaResponseDTO>> getAllComandas() {
        List<ComandaResponseDTO> comandas = comandaService.getAllComandas();
        return new ResponseEntity<>(comandas, HttpStatus.OK);
    }

}
