package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.request.CardapioRequestDTO;
import com.ufc.easydesk.api.http.request.ItemRequestDTO;
import com.ufc.easydesk.api.http.response.CardapioResponseDTO;
import com.ufc.easydesk.api.http.response.ItemResponseDTO;
import com.ufc.easydesk.domain.enums.Categoria;
import com.ufc.easydesk.service.CardapioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/{cardapioId}/itens")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<CardapioResponseDTO> addItemsToCardapio(
            @PathVariable Long cardapioId,
            @RequestBody List<ItemRequestDTO> itens) {
        CardapioResponseDTO updatedCardapio = cardapioService.addItemsToCardapio(cardapioId, itens);
        return new ResponseEntity<>(updatedCardapio, HttpStatus.OK);
    }

    @PutMapping("/{cardapioId}/itens/{itemId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<ItemResponseDTO> updateItem(
            @PathVariable Long cardapioId,
            @PathVariable Long itemId,
            @RequestBody ItemRequestDTO itemRequest) {
        ItemResponseDTO updatedItem = cardapioService.updateItem(cardapioId, itemId, itemRequest);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    @DeleteMapping("/{cardapioId}/itens/{itemId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<Void> deleteItem(
            @PathVariable Long cardapioId,
            @PathVariable Long itemId) {
        cardapioService.deleteItem(cardapioId, itemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{cardapioId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<CardapioResponseDTO> updateCardapio(
            @PathVariable Long cardapioId,
            @RequestBody CardapioRequestDTO request) {
        CardapioResponseDTO updatedCardapio = cardapioService.updateCardapio(cardapioId, request);
        return new ResponseEntity<>(updatedCardapio, HttpStatus.OK);
    }









}
