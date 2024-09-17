package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.request.CardapioRequestDTO;
import com.ufc.easydesk.api.http.response.CardapioResponseDTO;
import com.ufc.easydesk.service.CardapioService;
import com.ufc.easydesk.model.enums.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CardapioControllerTest {

    @Mock
    private CardapioService cardapioService;

    @InjectMocks
    private CardapioController cardapioController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createCardapio() {
        CardapioRequestDTO cardapioRequestDTO = new CardapioRequestDTO();
        CardapioResponseDTO cardapioResponseDTO = new CardapioResponseDTO();

        when(cardapioService.createCardapio(any(CardapioRequestDTO.class))).thenReturn(cardapioResponseDTO);

        ResponseEntity<CardapioResponseDTO> response = cardapioController.createCardapio(cardapioRequestDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cardapioResponseDTO, response.getBody());
    }

    @Test
    void getCardapioByCategoria() {
        Long restauranteId = 1L;
        Categoria categoria = Categoria.BEBIDA;
        CardapioResponseDTO cardapioResponseDTO = new CardapioResponseDTO();

        when(cardapioService.getCardapioByCategoria(restauranteId, categoria)).thenReturn(cardapioResponseDTO);

        ResponseEntity<CardapioResponseDTO> response = cardapioController.getCardapioByCategoria(restauranteId, categoria);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cardapioResponseDTO, response.getBody());
    }

    @Test
    void getCardapioCompleto() {
        Long restauranteId = 1L;
        CardapioResponseDTO cardapioResponseDTO = new CardapioResponseDTO();

        when(cardapioService.getCardapioCompleto(restauranteId)).thenReturn(cardapioResponseDTO);

        ResponseEntity<CardapioResponseDTO> response = cardapioController.getCardapioCompleto(restauranteId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cardapioResponseDTO, response.getBody());
    }
}