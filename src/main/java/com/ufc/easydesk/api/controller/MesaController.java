package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.request.MesaDisponibilidadeRequest;
import com.ufc.easydesk.api.http.request.MesaRequest;
import com.ufc.easydesk.api.http.response.MesaResponseDTO;
import com.ufc.easydesk.domain.model.Mesa;
import com.ufc.easydesk.service.MesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Mesas", description = "Gerenciamento de Mesas")
@RestController
@RequestMapping("/api/mesa")
@RequiredArgsConstructor
public class MesaController {

    private final MesaService mesaService;

    @Operation(summary = "Criar Mesa", description = "Cria uma nova mesa para o restaurante.")
    @ApiResponse(responseCode = "201", description = "Mesa criada com sucesso.")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<MesaResponseDTO> createMesa(@RequestBody MesaRequest request) {
        Mesa mesa = new Mesa();
        mesa.setNumeroMesa(request.getNumeroMesa());
        mesa.setDisponibilidade(request.getDisponibilidade());
        MesaResponseDTO createdMesa = mesaService.createMesa(mesa);
        return new ResponseEntity<>(createdMesa, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar Mesas", description = "Retorna a lista de todas as mesas.")
    @ApiResponse(responseCode = "200", description = "Mesas listadas com sucesso.")
    @GetMapping
    public ResponseEntity<List<MesaResponseDTO>> getMesas() {
        List<MesaResponseDTO> mesas = mesaService.getMesasByRestaurante();
        return new ResponseEntity<>(mesas, HttpStatus.OK);
    }

    @Operation(summary = "Alterar Disponibilidade da Mesa", description = "Altera a disponibilidade de uma mesa.")
    @ApiResponse(responseCode = "200", description = "Disponibilidade da mesa alterada com sucesso.")
    @PutMapping("/{mesaId}/disponibilidade")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<MesaResponseDTO> alterarDisponibilidadeMesa(
            @PathVariable Long mesaId,
            @RequestBody MesaDisponibilidadeRequest disponibilidadeRequest) {
        MesaResponseDTO updatedMesa = mesaService.alterarDisponibilidadeMesa(mesaId, disponibilidadeRequest.getDisponibilidade());
        return new ResponseEntity<>(updatedMesa, HttpStatus.OK);
    }

    @Operation(summary = "Deletar Mesa", description = "Deleta uma mesa pelo ID.")
    @ApiResponse(responseCode = "204", description = "Mesa deletada com sucesso.")
    @DeleteMapping("/{mesaId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<Void> deleteMesa(@PathVariable Long mesaId) {
        mesaService.deleteMesa(mesaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
