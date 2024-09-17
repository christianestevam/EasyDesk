package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.response.ClienteResponseDTO;
import com.ufc.easydesk.service.ClienteService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ActiveProfiles("test")
class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void testGetClienteLogado() throws Exception {
        ClienteResponseDTO mockCliente = new ClienteResponseDTO();
        mockCliente.setId(1L);
        mockCliente.setEmail("cliente@example.com");
        mockCliente.setNome("Cliente Teste");

        when(clienteService.getClienteLogado()).thenReturn(mockCliente);

        mockMvc.perform(get("/api/cliente")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("cliente@example.com"))
                .andExpect(jsonPath("$.nome").value("Cliente Teste"));
    }

}
