package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.request.MesaDisponibilidadeRequest;
import com.ufc.easydesk.api.http.request.MesaRequest;
import com.ufc.easydesk.api.http.response.MesaResponseDTO;
import com.ufc.easydesk.domain.model.Mesa;
import com.ufc.easydesk.service.MesaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesa")
@RequiredArgsConstructor
public class MesaController {

    private final MesaService mesaService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<MesaResponseDTO> createMesa(@RequestBody MesaRequest request) {
        Mesa mesa = new Mesa();
        mesa.setNumeroMesa(request.getNumeroMesa());
        mesa.setDisponibilidade(request.getDisponibilidade());
        MesaResponseDTO createdMesa = mesaService.createMesa(mesa);  // Removido restauranteId
        return new ResponseEntity<>(createdMesa, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MesaResponseDTO>> getMesas() {
        List<MesaResponseDTO> mesas = mesaService.getMesasByRestaurante();  // Removido restauranteId
        return new ResponseEntity<>(mesas, HttpStatus.OK);
    }

    @PutMapping("/{mesaId}/disponibilidade")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<MesaResponseDTO> alterarDisponibilidadeMesa(
            @PathVariable Long mesaId,
            @RequestBody MesaDisponibilidadeRequest disponibilidadeRequest) {
        MesaResponseDTO updatedMesa = mesaService.alterarDisponibilidadeMesa(mesaId, disponibilidadeRequest.getDisponibilidade());
        return new ResponseEntity<>(updatedMesa, HttpStatus.OK);
    }

    @DeleteMapping("/{mesaId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<Void> deleteMesa(@PathVariable Long mesaId) {
        mesaService.deleteMesa(mesaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
