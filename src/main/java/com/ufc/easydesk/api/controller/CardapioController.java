package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.request.CardapioRequestDTO;
import com.ufc.easydesk.api.http.request.ItemRequestDTO;
import com.ufc.easydesk.api.http.response.CardapioResponseDTO;
import com.ufc.easydesk.domain.enums.Categoria;
import com.ufc.easydesk.service.CardapioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/cardapio")
@RequiredArgsConstructor
public class CardapioController {

    private final CardapioService cardapioService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<CardapioResponseDTO> createCardapio(@RequestBody CardapioRequestDTO request) {
        CardapioResponseDTO createdCardapio = cardapioService.createCardapio(request);
        return new ResponseEntity<>(createdCardapio, HttpStatus.CREATED);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<CardapioResponseDTO> getCardapioByCategoria(@PathVariable Categoria categoria) {
        CardapioResponseDTO cardapioFiltrado = cardapioService.getCardapioByCategoria(categoria);
        return new ResponseEntity<>(cardapioFiltrado, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CardapioResponseDTO> getCardapioCompleto() {
        CardapioResponseDTO cardapioCompleto = cardapioService.getCardapioCompleto();
        return new ResponseEntity<>(cardapioCompleto, HttpStatus.OK);
    }

    @PutMapping("/{cardapioId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<CardapioResponseDTO> updateCardapio(
            @PathVariable Long cardapioId, @RequestBody CardapioRequestDTO request) {
        CardapioResponseDTO updatedCardapio = cardapioService.updateCardapio(cardapioId, request);
        return new ResponseEntity<>(updatedCardapio, HttpStatus.OK);
    }
}
