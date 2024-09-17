package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.request.CardapioRequestDTO;
import com.ufc.easydesk.api.http.response.CardapioResponseDTO;
import com.ufc.easydesk.domain.enums.Categoria;
import com.ufc.easydesk.service.CardapioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CardapioControllerTest {

    @Mock
    private CardapioService cardapioService;

    @InjectMocks
    private CardapioController cardapioController;

    private CardapioResponseDTO cardapioResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cardapioResponseDTO = new CardapioResponseDTO();
        cardapioResponseDTO.setId(1L);
        cardapioResponseDTO.setItens(new ArrayList<>());
    }

    @Test
    void testCreateCardapio() {
        CardapioRequestDTO request = new CardapioRequestDTO();
        when(cardapioService.createCardapio(request)).thenReturn(cardapioResponseDTO);

        ResponseEntity<CardapioResponseDTO> response = cardapioController.createCardapio(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cardapioResponseDTO, response.getBody());
        verify(cardapioService, times(1)).createCardapio(request);
    }

    @Test
    void testGetCardapioByCategoria() {
        Categoria categoria = Categoria.BEBIDA;
        when(cardapioService.getCardapioByCategoria(categoria)).thenReturn(cardapioResponseDTO);

        ResponseEntity<CardapioResponseDTO> response = cardapioController.getCardapioByCategoria(categoria);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cardapioResponseDTO, response.getBody());
        verify(cardapioService, times(1)).getCardapioByCategoria(categoria);
    }

    @Test
    void testGetCardapioCompleto() {
        when(cardapioService.getCardapioCompleto()).thenReturn(cardapioResponseDTO);

        ResponseEntity<CardapioResponseDTO> response = cardapioController.getCardapioCompleto();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cardapioResponseDTO, response.getBody());
        verify(cardapioService, times(1)).getCardapioCompleto();
    }

    @Test
    void testUpdateCardapio() {
        Long cardapioId = 1L;
        CardapioRequestDTO request = new CardapioRequestDTO();
        when(cardapioService.updateCardapio(cardapioId, request)).thenReturn(cardapioResponseDTO);

        ResponseEntity<CardapioResponseDTO> response = cardapioController.updateCardapio(cardapioId, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cardapioResponseDTO, response.getBody());
        verify(cardapioService, times(1)).updateCardapio(cardapioId, request);
    }
}
