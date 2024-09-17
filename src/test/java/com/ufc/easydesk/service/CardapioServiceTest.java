package com.ufc.easydesk.service;

import com.ufc.easydesk.api.http.request.CardapioRequestDTO;
import com.ufc.easydesk.api.http.request.ItemRequestDTO;
import com.ufc.easydesk.api.http.response.CardapioResponseDTO;
import com.ufc.easydesk.domain.enums.Categoria;
import com.ufc.easydesk.domain.model.Cardapio;
import com.ufc.easydesk.domain.model.Cliente;
import com.ufc.easydesk.domain.model.Item;
import com.ufc.easydesk.domain.model.Restaurante;
import com.ufc.easydesk.domain.repository.CardapioRepository;
import com.ufc.easydesk.domain.repository.ClienteRepository;
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
    private ClienteRepository clienteRepository;

    @InjectMocks
    private CardapioService cardapioService;

    private Cliente cliente;
    private Restaurante restaurante;
    private Cardapio cardapio;
    private CardapioRequestDTO cardapioRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Criando e configurando o Restaurante e associando ao Cliente
        cliente = new Cliente();
        cliente.setEmail("cliente@example.com");

        restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setProprietario(cliente);
        cliente.setRestaurante(restaurante);  // Associando o restaurante ao cliente

        cardapio = new Cardapio();
        cardapio.setId(1L);
        cardapio.setRestaurante(restaurante);
        cardapio.setItens(new ArrayList<>());

        cardapioRequestDTO = new CardapioRequestDTO();
        cardapioRequestDTO.setItens(List.of(new ItemRequestDTO("Pizza", "Deliciosa pizza", 25.0, Categoria.PIZZA, true)));

        // Simulando o contexto de autenticação
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(cliente.getEmail());

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testCreateCardapio() {
        when(clienteRepository.findByEmail(anyString())).thenReturn(Optional.of(cliente));
        when(cardapioRepository.save(any(Cardapio.class))).thenReturn(cardapio);

        CardapioResponseDTO response = cardapioService.createCardapio(cardapioRequestDTO);

        assertNotNull(response);
        assertEquals(cardapio.getId(), response.getId());
        verify(cardapioRepository, times(1)).save(any(Cardapio.class));
    }

    @Test
    void testGetCardapioByCategoria() {
        when(clienteRepository.findByEmail(anyString())).thenReturn(Optional.of(cliente));
        when(cardapioRepository.findByRestauranteId(anyLong())).thenReturn(Optional.of(cardapio));

        CardapioResponseDTO response = cardapioService.getCardapioByCategoria(Categoria.PIZZA);

        assertNotNull(response);
        assertEquals(cardapio.getId(), response.getId());
        verify(cardapioRepository, times(1)).findByRestauranteId(anyLong());
    }

    @Test
    void testGetCardapioCompleto() {
        when(clienteRepository.findByEmail(anyString())).thenReturn(Optional.of(cliente));
        when(cardapioRepository.findByRestauranteId(anyLong())).thenReturn(Optional.of(cardapio));

        CardapioResponseDTO response = cardapioService.getCardapioCompleto();

        assertNotNull(response);
        assertEquals(cardapio.getId(), response.getId());
        verify(cardapioRepository, times(1)).findByRestauranteId(anyLong());
    }

    @Test
    void testUpdateCardapio() {
        Long cardapioId = 1L;

        // Criando um item com a categoria corretamente definida e utilizando ArrayList para permitir mutação
        Item item = new Item(null, "Pizza", "Deliciosa pizza", 25.0, Categoria.PIZZA, true);
        cardapio.setItens(new ArrayList<>(List.of(item))); // Usando ArrayList para permitir mutação

        // Mockando as respostas
        when(cardapioRepository.findById(cardapioId)).thenReturn(Optional.of(cardapio));
        when(cardapioRepository.save(any(Cardapio.class))).thenReturn(cardapio);

        // Executando o método de atualização
        CardapioResponseDTO response = cardapioService.updateCardapio(cardapioId, cardapioRequestDTO);

        // Assegurando que o cardápio foi atualizado corretamente
        assertNotNull(response);
        assertEquals(cardapio.getId(), response.getId());
        verify(cardapioRepository, times(1)).save(any(Cardapio.class));
    }


}
