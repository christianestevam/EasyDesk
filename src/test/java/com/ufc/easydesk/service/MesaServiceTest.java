package com.ufc.easydesk.service;

import com.ufc.easydesk.api.http.response.MesaResponseDTO;
import com.ufc.easydesk.model.Cliente;
import com.ufc.easydesk.model.Mesa;
import com.ufc.easydesk.model.Restaurante;
import com.ufc.easydesk.repository.MesaRepository;
import com.ufc.easydesk.repository.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

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

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private MesaService mesaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
    }

    @Test
    void createMesa() {
        Mesa mesa = new Mesa();
        mesa.setNumeroMesa(5L);

        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        Cliente proprietario = new Cliente();
        proprietario.setEmail("test@example.com");
        restaurante.setProprietario(proprietario);

        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(restaurante));
        when(mesaRepository.save(any(Mesa.class))).thenReturn(mesa);

        MesaResponseDTO response = mesaService.createMesa(mesa, 1L);

        assertNotNull(response);
        assertEquals(5L, response.getNumeroMesa());
        verify(mesaRepository, times(1)).save(any(Mesa.class));
    }

    @Test
    void getMesasByRestaurante() {
        Long restauranteId = 1L;

        Restaurante restaurante = new Restaurante();
        restaurante.setId(restauranteId);
        Cliente proprietario = new Cliente();
        proprietario.setEmail("test@example.com");
        restaurante.setProprietario(proprietario);

        Mesa mesa1 = new Mesa();
        mesa1.setNumeroMesa(1L);
        mesa1.setRestaurante(restaurante);

        Mesa mesa2 = new Mesa();
        mesa2.setNumeroMesa(2L);
        mesa2.setRestaurante(restaurante);

        when(restauranteRepository.findById(restauranteId)).thenReturn(Optional.of(restaurante));
        when(mesaRepository.findByRestauranteId(restauranteId)).thenReturn(List.of(mesa1, mesa2));

        List<MesaResponseDTO> response = mesaService.getMesasByRestaurante(restauranteId);

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(1L, response.get(0).getNumeroMesa());
        assertEquals(2L, response.get(1).getNumeroMesa());
        verify(mesaRepository, times(1)).findByRestauranteId(restauranteId);
    }
}