package com.ufc.easydesk.service;

import com.ufc.easydesk.api.http.request.CardapioRequestDTO;
import com.ufc.easydesk.api.http.response.CardapioResponseDTO;
import com.ufc.easydesk.model.Cardapio;
import com.ufc.easydesk.model.Cliente;
import com.ufc.easydesk.model.Item;
import com.ufc.easydesk.model.Restaurante;
import com.ufc.easydesk.model.enums.Categoria;
import com.ufc.easydesk.repository.CardapioRepository;
import com.ufc.easydesk.repository.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardapioServiceTest {

    @Mock
    private CardapioRepository cardapioRepository;

    @Mock
    private RestauranteRepository restauranteRepository;

    @InjectMocks
    private CardapioService cardapioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCardapio() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("test@example.com");

        CardapioRequestDTO request = new CardapioRequestDTO();
        request.setRestauranteId(1L);
        request.setItens(new ArrayList<>());

        Cliente proprietario = new Cliente();
        proprietario.setEmail("test@example.com");

        Restaurante restaurante = new Restaurante();
        restaurante.setProprietario(proprietario);
        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(restaurante));

        Cardapio cardapio = new Cardapio();
        cardapio.setItens(new ArrayList<>());
        when(cardapioRepository.save(any(Cardapio.class))).thenReturn(cardapio);

        CardapioResponseDTO response = cardapioService.createCardapio(request);

        assertNotNull(response);
        verify(cardapioRepository, times(1)).save(any(Cardapio.class));
    }

    @Test
    void getCardapioByCategoria() {
        Long restauranteId = 1L;
        Categoria categoria = Categoria.BEBIDA;

        Cardapio cardapio = new Cardapio();
        Item item = new Item();
        item.setCategoria(categoria);
        cardapio.setItens(List.of(item));
        when(cardapioRepository.findByRestauranteId(restauranteId)).thenReturn(Optional.of(cardapio));

        CardapioResponseDTO response = cardapioService.getCardapioByCategoria(restauranteId, categoria);

        assertNotNull(response);
        assertEquals(1, response.getItens().size());
        assertEquals(categoria.name(), response.getItens().get(0).getCategoria());
    }

    @Test
    void getCardapioCompleto() {
        Long restauranteId = 1L;

        Restaurante restaurante = new Restaurante();
        when(restauranteRepository.findById(restauranteId)).thenReturn(Optional.of(restaurante));

        Cardapio cardapio = new Cardapio();
        cardapio.setItens(new ArrayList<>());
        when(cardapioRepository.findByRestauranteId(restauranteId)).thenReturn(Optional.of(cardapio));

        CardapioResponseDTO response = cardapioService.getCardapioCompleto(restauranteId);

        assertNotNull(response);
        verify(cardapioRepository, times(1)).findByRestauranteId(restauranteId);
    }
}