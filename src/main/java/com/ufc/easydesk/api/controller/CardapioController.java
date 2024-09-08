package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.request.CardapioRequestDTO;
import com.ufc.easydesk.api.http.response.CardapioResponseDTO;
import com.ufc.easydesk.model.enums.Categoria;
import com.ufc.easydesk.service.CardapioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cardapios")
@RequiredArgsConstructor
public class CardapioController {

    private final CardapioService cardapioService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<CardapioResponseDTO> createCardapio(@RequestBody CardapioRequestDTO request) {
        CardapioResponseDTO createdCardapio = cardapioService.createCardapio(request);
        return new ResponseEntity<>(createdCardapio, HttpStatus.CREATED);
    }

    @GetMapping("/restaurante/{restauranteId}/categoria/{categoria}")
    public ResponseEntity<CardapioResponseDTO> getCardapioByCategoria(
            @PathVariable Long restauranteId, @PathVariable Categoria categoria) {
        CardapioResponseDTO cardapioFiltrado = cardapioService.getCardapioByCategoria(restauranteId, categoria);
        return new ResponseEntity<>(cardapioFiltrado, HttpStatus.OK);
    }

    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<CardapioResponseDTO> getCardapioCompleto(@PathVariable Long restauranteId) {
        CardapioResponseDTO cardapioCompleto = cardapioService.getCardapioCompleto(restauranteId);
        return new ResponseEntity<>(cardapioCompleto, HttpStatus.OK);
    }
}
