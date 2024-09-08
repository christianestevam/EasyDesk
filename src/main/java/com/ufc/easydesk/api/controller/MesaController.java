package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.request.MesaRequest;
import com.ufc.easydesk.api.http.response.MesaResponseDTO;
import com.ufc.easydesk.model.Mesa;
import com.ufc.easydesk.service.MesaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mesas")
@RequiredArgsConstructor
public class MesaController {

    private final MesaService mesaService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<MesaResponseDTO> createMesa(@RequestBody MesaRequest request) {
        Mesa mesa = new Mesa();
        mesa.setNumeroMesa(request.getNumeroMesa());
        mesa.setDisponibilidade(request.getDisponibilidade());
        MesaResponseDTO createdMesa = mesaService.createMesa(mesa, request.getRestauranteId());
        return new ResponseEntity<>(createdMesa, HttpStatus.CREATED);
    }

    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<MesaResponseDTO>> getMesasByRestaurante(@PathVariable Long restauranteId) {
        List<MesaResponseDTO> mesas = mesaService.getMesasByRestaurante(restauranteId);
        return new ResponseEntity<>(mesas, HttpStatus.OK);
    }
}
