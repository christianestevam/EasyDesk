package com.ufc.easydesk.service;

import com.ufc.easydesk.api.http.response.MesaResponseDTO;
import com.ufc.easydesk.domain.model.Mesa;
import com.ufc.easydesk.domain.model.Restaurante;
import com.ufc.easydesk.domain.repository.MesaRepository;
import com.ufc.easydesk.domain.repository.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MesaServiceTest {

    @Mock
    private MesaRepository mesaRepository;

    @Mock
    private RestauranteRepository restauranteRepository;

    @InjectMocks
    private MesaService mesaService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateMesa() {
        // Configura o contexto de segurança manualmente
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("testuser@example.com", null, new ArrayList<>()));

        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);

        Mesa mesa = new Mesa();
        mesa.setNumeroMesa(10L);

        when(restauranteRepository.findByProprietarioEmail("testuser@example.com")).thenReturn(Optional.of(restaurante));
        when(mesaRepository.save(any(Mesa.class))).thenReturn(mesa);

        MesaResponseDTO response = mesaService.createMesa(mesa);

        assertNotNull(response);
        assertEquals(10L, response.getNumeroMesa());
        verify(mesaRepository, times(1)).save(any(Mesa.class));
    }


    @Test
    @WithMockUser(username = "testuser@example.com")
    void testAlterarDisponibilidadeMesa() {
        Mesa mesa = new Mesa();
        mesa.setId(1L);
        mesa.setNumeroMesa(10L);
        mesa.setDisponibilidade(true);

        when(mesaRepository.findById(1L)).thenReturn(Optional.of(mesa));
        when(mesaRepository.save(any(Mesa.class))).thenReturn(mesa);

        MesaResponseDTO response = mesaService.alterarDisponibilidadeMesa(1L, false);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertFalse(response.getDisponibilidade());
        verify(mesaRepository, times(1)).save(any(Mesa.class));
    }

    @Test
    void testGetMesasByRestaurante() {
        // Criando mocks manuais do SecurityContext e Authentication
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn("testuser@example.com");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext); // Setando o contexto de segurança mockado

        // Criando o restaurante simulado
        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);

        // Criando mesas simuladas
        Mesa mesa1 = new Mesa();
        mesa1.setId(1L);
        mesa1.setNumeroMesa(10L);

        Mesa mesa2 = new Mesa();
        mesa2.setId(2L);
        mesa2.setNumeroMesa(20L);

        // Mockando os repositórios
        when(restauranteRepository.findByProprietarioEmail("testuser@example.com")).thenReturn(Optional.of(restaurante));
        when(mesaRepository.findByRestauranteId(1L)).thenReturn(List.of(mesa1, mesa2));

        // Executando o serviço
        List<MesaResponseDTO> response = mesaService.getMesasByRestaurante();

        // Verificando a resposta
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(1L, response.get(0).getId());
        assertEquals(10L, response.get(0).getNumeroMesa());
        assertEquals(2L, response.get(1).getId());
        assertEquals(20L, response.get(1).getNumeroMesa());

        // Verificando as interações com os mocks
        verify(mesaRepository, times(1)).findByRestauranteId(1L);
        verify(restauranteRepository, times(1)).findByProprietarioEmail("testuser@example.com");
    }
}
