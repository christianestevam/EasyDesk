package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.request.CardapioRequestDTO;
import com.ufc.easydesk.api.http.request.ItemRequestDTO;
import com.ufc.easydesk.api.http.response.CardapioResponseDTO;
import com.ufc.easydesk.domain.enums.Categoria;
import com.ufc.easydesk.service.CardapioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Criar novo cardápio", description = "Cria um novo cardápio para o restaurante do cliente.")
    @ApiResponse(responseCode = "201", description = "Cardápio criado com sucesso")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<CardapioResponseDTO> createCardapio(@RequestBody CardapioRequestDTO request) {
        CardapioResponseDTO createdCardapio = cardapioService.createCardapio(request);
        return new ResponseEntity<>(createdCardapio, HttpStatus.CREATED);
    }

    @Operation(summary = "Obter cardápio por categoria", description = "Busca itens de um cardápio por uma categoria específica.")
    @ApiResponse(responseCode = "200", description = "Cardápio filtrado por categoria")
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<CardapioResponseDTO> getCardapioByCategoria(@PathVariable Categoria categoria) {
        CardapioResponseDTO cardapioFiltrado = cardapioService.getCardapioByCategoria(categoria);
        return new ResponseEntity<>(cardapioFiltrado, HttpStatus.OK);
    }

    @Operation(summary = "Obter cardápio completo", description = "Retorna o cardápio completo do restaurante.")
    @ApiResponse(responseCode = "200", description = "Cardápio completo")
    @GetMapping
    public ResponseEntity<CardapioResponseDTO> getCardapioCompleto() {
        CardapioResponseDTO cardapioCompleto = cardapioService.getCardapioCompleto();
        return new ResponseEntity<>(cardapioCompleto, HttpStatus.OK);
    }

    @Operation(summary = "Atualizar cardápio", description = "Atualiza todos os itens do cardápio.")
    @ApiResponse(responseCode = "200", description = "Cardápio atualizado com sucesso")
    @PutMapping("/{cardapioId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<CardapioResponseDTO> updateCardapio(@PathVariable Long cardapioId, @RequestBody CardapioRequestDTO request) {
        CardapioResponseDTO updatedCardapio = cardapioService.updateCardapio(cardapioId, request);
        return new ResponseEntity<>(updatedCardapio, HttpStatus.OK);
    }

}
