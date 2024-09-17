package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.request.MesaDisponibilidadeRequest;
import com.ufc.easydesk.api.http.request.MesaRequest;
import com.ufc.easydesk.api.http.response.MesaResponseDTO;
import com.ufc.easydesk.domain.model.Mesa;
import com.ufc.easydesk.service.MesaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MesaControllerTest {

    @Mock
    private MesaService mesaService;

    @InjectMocks
    private MesaController mesaController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(mesaController).build();
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "GERENTE"})
    void testCreateMesa() throws Exception {
        MesaRequest mesaRequest = new MesaRequest();
        mesaRequest.setNumeroMesa(5L);
        mesaRequest.setDisponibilidade(true);

        MesaResponseDTO mesaResponse = new MesaResponseDTO();
        mesaResponse.setNumeroMesa(5L);
        mesaResponse.setDisponibilidade(true);

        when(mesaService.createMesa(any(Mesa.class))).thenReturn(mesaResponse);

        mockMvc.perform(post("/api/mesa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"numeroMesa\": 5, \"disponibilidade\": true}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroMesa").value(5))
                .andExpect(jsonPath("$.disponibilidade").value(true));

        verify(mesaService, times(1)).createMesa(any(Mesa.class));
    }

    @Test
    void testGetMesas() throws Exception {
        MesaResponseDTO mesaResponse1 = new MesaResponseDTO();
        mesaResponse1.setNumeroMesa(5L);
        mesaResponse1.setDisponibilidade(true);

        MesaResponseDTO mesaResponse2 = new MesaResponseDTO();
        mesaResponse2.setNumeroMesa(10L);
        mesaResponse2.setDisponibilidade(false);

        when(mesaService.getMesasByRestaurante()).thenReturn(List.of(mesaResponse1, mesaResponse2));

        mockMvc.perform(get("/api/mesa")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroMesa").value(5))
                .andExpect(jsonPath("$[0].disponibilidade").value(true))
                .andExpect(jsonPath("$[1].numeroMesa").value(10))
                .andExpect(jsonPath("$[1].disponibilidade").value(false));

        verify(mesaService, times(1)).getMesasByRestaurante();
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "GERENTE"})
    void testAlterarDisponibilidadeMesa() throws Exception {
        MesaDisponibilidadeRequest disponibilidadeRequest = new MesaDisponibilidadeRequest();
        disponibilidadeRequest.setDisponibilidade(false);

        MesaResponseDTO mesaResponse = new MesaResponseDTO();
        mesaResponse.setNumeroMesa(5L);
        mesaResponse.setDisponibilidade(false);

        when(mesaService.alterarDisponibilidadeMesa(anyLong(), anyBoolean())).thenReturn(mesaResponse);

        mockMvc.perform(put("/api/mesa/5/disponibilidade")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"disponibilidade\": false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroMesa").value(5))
                .andExpect(jsonPath("$.disponibilidade").value(false));

        verify(mesaService, times(1)).alterarDisponibilidadeMesa(eq(5L), eq(false));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "GERENTE"})
    void testDeleteMesa() throws Exception {
        doNothing().when(mesaService).deleteMesa(5L);

        mockMvc.perform(delete("/api/mesa/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(mesaService, times(1)).deleteMesa(5L);
    }
}
