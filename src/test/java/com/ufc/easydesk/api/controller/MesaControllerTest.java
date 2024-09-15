package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.request.MesaRequest;
import com.ufc.easydesk.api.http.response.MesaResponseDTO;
import com.ufc.easydesk.model.Mesa;
import com.ufc.easydesk.service.MesaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MesaControllerTest {

    @Mock
    private MesaService mesaService;

    @InjectMocks
    private MesaController mesaController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMesa() {
        MesaRequest mesaRequest = new MesaRequest();
        mesaRequest.setNumeroMesa(1L);
        mesaRequest.setDisponibilidade(true);
        mesaRequest.setRestauranteId(1L);

        MesaResponseDTO mesaResponseDTO = new MesaResponseDTO();
        mesaResponseDTO.setNumeroMesa(1L);
        mesaResponseDTO.setDisponibilidade(true);

        when(mesaService.createMesa(any(Mesa.class), any(Long.class))).thenReturn(mesaResponseDTO);

        ResponseEntity<MesaResponseDTO> response = mesaController.createMesa(mesaRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mesaResponseDTO, response.getBody());
    }


    @Test
    void getMesasByRestaurante() {
        Long restauranteId = 1L;
        List<MesaResponseDTO> mesas = new ArrayList<>();
        MesaResponseDTO mesaResponseDTO = new MesaResponseDTO();
        mesaResponseDTO.setNumeroMesa(1L);
        mesaResponseDTO.setDisponibilidade(true);
        mesas.add(mesaResponseDTO);

        when(mesaService.getMesasByRestaurante(restauranteId)).thenReturn(mesas);

        ResponseEntity<List<MesaResponseDTO>> response = mesaController.getMesasByRestaurante(restauranteId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mesas, response.getBody());
    }
}
