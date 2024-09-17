package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.request.ComandaRequestDTO;
import com.ufc.easydesk.api.http.request.ComandaStatusRequestDTO;
import com.ufc.easydesk.api.http.response.ComandaResponseDTO;
import com.ufc.easydesk.api.http.response.ItemResponseDTO;
import com.ufc.easydesk.domain.enums.Status;
import com.ufc.easydesk.service.ComandaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
class ComandaControllerTest {

    @Mock
    private ComandaService comandaService;

    @InjectMocks
    private ComandaController comandaController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(comandaController).build();
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testCreateComanda() throws Exception {
        ComandaResponseDTO mockResponse = new ComandaResponseDTO();
        mockResponse.setId(1L);
        mockResponse.setNumeroMesa(12);
        mockResponse.setNomeConsumidor("John Doe");
        mockResponse.setItens(List.of(new ItemResponseDTO(1L, "Pizza", "Pizza de calabresa", 50.0, "PIZZA", true)));
        mockResponse.setTotal(60.0);
        mockResponse.setTaxaServico(10.0);
        mockResponse.setAtiva(true);
        mockResponse.setStatus("ABERTO");

        ComandaRequestDTO mockRequest = new ComandaRequestDTO();
        mockRequest.setMesaId(1L);
        mockRequest.setNomeConsumidor("John Doe");
        mockRequest.setObservacao("Sem cebola");
        mockRequest.setItens(List.of(1L));
        mockRequest.setTaxaServico(10.0);

        when(comandaService.createComanda(any(ComandaRequestDTO.class))).thenReturn(mockResponse);

        String requestJson = """
            {
              "mesaId": 1,
              "nomeConsumidor": "John Doe",
              "observacao": "Sem cebola",
              "itens": [1],
              "taxaServico": 10.0
            }
        """;

        mockMvc.perform(post("/api/comanda")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.numeroMesa").value(12))
                .andExpect(jsonPath("$.nomeConsumidor").value("John Doe"))
                .andExpect(jsonPath("$.total").value(60.0));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testFecharComanda() throws Exception {
        ComandaResponseDTO mockResponse = new ComandaResponseDTO();
        mockResponse.setId(1L);
        mockResponse.setNumeroMesa(12);
        mockResponse.setNomeConsumidor("John Doe");
        mockResponse.setItens(List.of(new ItemResponseDTO(1L, "Pizza", "Pizza de calabresa", 50.0, "PIZZA", true)));
        mockResponse.setTotal(60.0);
        mockResponse.setTaxaServico(10.0);
        mockResponse.setAtiva(false);
        mockResponse.setStatus("FECHADA");

        when(comandaService.fecharComanda(1L)).thenReturn(mockResponse);

        mockMvc.perform(put("/api/comanda/1/fechar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("FECHADA"))
                .andExpect(jsonPath("$.ativa").value(false));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testAtualizarStatusComanda() throws Exception {
        ComandaResponseDTO mockResponse = new ComandaResponseDTO();
        mockResponse.setId(1L);
        mockResponse.setNumeroMesa(12);
        mockResponse.setNomeConsumidor("John Doe");
        mockResponse.setItens(List.of(new ItemResponseDTO(1L, "Pizza", "Pizza de calabresa", 50.0, "PIZZA", true)));
        mockResponse.setTotal(60.0);
        mockResponse.setTaxaServico(10.0);
        mockResponse.setAtiva(false);
        mockResponse.setStatus("PAGA");

        ComandaStatusRequestDTO mockRequest = new ComandaStatusRequestDTO();
        mockRequest.setStatus(Status.PAGA);

        when(comandaService.atualizarStatus(eq(1L), any(ComandaStatusRequestDTO.class))).thenReturn(mockResponse);

        String requestJson = """
            {
              "status": "PAGA"
            }
        """;

        mockMvc.perform(put("/api/comanda/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("PAGA"))
                .andExpect(jsonPath("$.ativa").value(false));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testGetComandaById() throws Exception {
        ComandaResponseDTO mockResponse = new ComandaResponseDTO();
        mockResponse.setId(1L);
        mockResponse.setNumeroMesa(12);
        mockResponse.setNomeConsumidor("John Doe");
        mockResponse.setItens(List.of(new ItemResponseDTO(1L, "Pizza", "Pizza de calabresa", 50.0, "PIZZA", true)));
        mockResponse.setTotal(60.0);
        mockResponse.setTaxaServico(10.0);
        mockResponse.setAtiva(true);
        mockResponse.setStatus("ABERTO");

        when(comandaService.getComandaById(1L)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/comanda/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.numeroMesa").value(12))
                .andExpect(jsonPath("$.nomeConsumidor").value("John Doe"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testGetAllComandas() throws Exception {
        ComandaResponseDTO mockResponse1 = new ComandaResponseDTO();
        mockResponse1.setId(1L);
        mockResponse1.setNumeroMesa(12);
        mockResponse1.setNomeConsumidor("John Doe");

        ComandaResponseDTO mockResponse2 = new ComandaResponseDTO();
        mockResponse2.setId(2L);
        mockResponse2.setNumeroMesa(15);
        mockResponse2.setNomeConsumidor("Jane Smith");

        when(comandaService.getAllComandas()).thenReturn(List.of(mockResponse1, mockResponse2));

        mockMvc.perform(get("/api/comanda")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nomeConsumidor").value("John Doe"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nomeConsumidor").value("Jane Smith"));
    }
}
