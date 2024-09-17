package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.request.ComandaRequestDTO;
import com.ufc.easydesk.api.http.request.ComandaStatusRequestDTO;
import com.ufc.easydesk.api.http.response.ComandaResponseDTO;
import com.ufc.easydesk.model.enums.Status;
import com.ufc.easydesk.service.ComandaService;
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

public class ComandaControllerTest {

    @Mock
    private ComandaService comandaService;

    @InjectMocks
    private ComandaController comandaController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createComanda() {
        ComandaRequestDTO comandaRequestDTO = new ComandaRequestDTO();
        ComandaResponseDTO comandaResponseDTO = new ComandaResponseDTO();

        when(comandaService.createComanda(any(ComandaRequestDTO.class))).thenReturn(comandaResponseDTO);

        ResponseEntity<ComandaResponseDTO> response = comandaController.createComanda(comandaRequestDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(comandaResponseDTO, response.getBody());
    }

    @Test
    void fecharComanda() {
        Long comandaId = 1L;
        ComandaResponseDTO comandaResponseDTO = new ComandaResponseDTO();

        when(comandaService.fecharComanda(comandaId)).thenReturn(comandaResponseDTO);

        ResponseEntity<ComandaResponseDTO> response = comandaController.fecharComanda(comandaId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comandaResponseDTO, response.getBody());
    }

    @Test
    void atualizarStatusComanda() {
        Long comandaId = 1L;
        ComandaStatusRequestDTO statusRequestDTO = new ComandaStatusRequestDTO();
        statusRequestDTO.setStatus(Status.FECHADA);  // Assumindo que Status é um enum e FECHADA é um valor valido
        ComandaResponseDTO comandaResponseDTO = new ComandaResponseDTO();

        when(comandaService.atualizarStatus(comandaId, statusRequestDTO)).thenReturn(comandaResponseDTO);

        ResponseEntity<ComandaResponseDTO> response = comandaController.atualizarStatusComanda(comandaId, statusRequestDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comandaResponseDTO, response.getBody());
    }

    @Test
    void getComandaById() {
        Long comandaId = 1L;
        ComandaResponseDTO comandaResponseDTO = new ComandaResponseDTO();

        when(comandaService.getComandaById(comandaId)).thenReturn(comandaResponseDTO);

        ResponseEntity<ComandaResponseDTO> response = comandaController.getComandaById(comandaId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(comandaResponseDTO, response.getBody());
    }
}