package com.ufc.easydesk.service;

import com.ufc.easydesk.api.http.request.ComandaRequestDTO;
import com.ufc.easydesk.api.http.request.ComandaStatusRequestDTO;
import com.ufc.easydesk.api.http.response.ComandaResponseDTO;
import com.ufc.easydesk.domain.enums.Categoria;
import com.ufc.easydesk.domain.enums.Status;
import com.ufc.easydesk.domain.model.Comanda;
import com.ufc.easydesk.domain.model.Item;
import com.ufc.easydesk.domain.model.Mesa;
import com.ufc.easydesk.domain.repository.ComandaRepository;
import com.ufc.easydesk.domain.repository.ItemRepository;
import com.ufc.easydesk.domain.repository.MesaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ComandaServiceTest {

    @Mock
    private ComandaRepository comandaRepository;

    @Mock
    private MesaRepository mesaRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ComandaService comandaService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateComanda() {
        ComandaRequestDTO request = new ComandaRequestDTO();
        request.setMesaId(1L);
        request.setNomeConsumidor("John Doe");
        request.setItens(List.of(1L));
        request.setTaxaServico(10.0);

        Mesa mesa = new Mesa();
        mesa.setId(1L);
        mesa.setNumeroMesa(12L);

        Item item = new Item();
        item.setId(1L);
        item.setNome("Pizza");
        item.setPreco(50.0);
        item.setCategoria(Categoria.PIZZA);

        when(mesaRepository.findById(1L)).thenReturn(Optional.of(mesa));
        when(itemRepository.findAllById(List.of(1L))).thenReturn(List.of(item));

        Comanda comanda = new Comanda();
        comanda.setItens(List.of(item));

        when(comandaRepository.save(any(Comanda.class))).thenAnswer(i -> {
            Comanda savedComanda = i.getArgument(0);
            savedComanda.setItens(List.of(item));
            return savedComanda;
        });

        ComandaResponseDTO response = comandaService.createComanda(request);

        assertNotNull(response);
        assertEquals(12, response.getNumeroMesa());
        assertEquals("John Doe", response.getNomeConsumidor());
        assertEquals(60.0, response.getTotal());
        verify(comandaRepository, times(1)).save(any(Comanda.class));
    }

    @Test
    void testFecharComanda() {
        Comanda comanda = new Comanda();
        comanda.setId(1L);
        comanda.setAtiva(true);
        comanda.setStatus(Status.ABERTO);

        Item item = new Item();
        item.setCategoria(Categoria.PIZZA);
        comanda.setItens(List.of(item));

        when(comandaRepository.findById(1L)).thenReturn(Optional.of(comanda));
        when(comandaRepository.save(any(Comanda.class))).thenAnswer(i -> i.getArgument(0));

        ComandaResponseDTO response = comandaService.fecharComanda(1L);

        assertNotNull(response);
        assertFalse(response.getAtiva());
        assertEquals("FECHADA", response.getStatus());
        verify(comandaRepository, times(1)).save(any(Comanda.class));
    }

    @Test
    void testAtualizarStatusComanda() {
        Comanda comanda = new Comanda();
        comanda.setId(1L);
        comanda.setAtiva(true);
        comanda.setStatus(Status.ABERTO);

        Item item = new Item();
        item.setCategoria(Categoria.PIZZA);
        comanda.setItens(List.of(item));

        ComandaStatusRequestDTO request = new ComandaStatusRequestDTO();
        request.setStatus(Status.PAGA);

        when(comandaRepository.findById(1L)).thenReturn(Optional.of(comanda));
        when(comandaRepository.save(any(Comanda.class))).thenAnswer(i -> i.getArgument(0));

        ComandaResponseDTO response = comandaService.atualizarStatus(1L, request);

        assertNotNull(response);
        assertEquals("PAGA", response.getStatus());
        assertFalse(response.getAtiva());
        verify(comandaRepository, times(1)).save(any(Comanda.class));
    }

    @Test
    void testGetComandaById() {

        Comanda comanda = new Comanda();
        comanda.setId(1L);
        comanda.setNomeConsumidor("John Doe");
        comanda.setStatus(Status.ABERTO);

        Item item = new Item();
        item.setId(1L);
        item.setNome("Pizza");
        item.setCategoria(Categoria.PIZZA);
        comanda.setItens(List.of(item));

        when(comandaRepository.findById(1L)).thenReturn(Optional.of(comanda));

        ComandaResponseDTO response = comandaService.getComandaById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("John Doe", response.getNomeConsumidor());
        assertEquals("ABERTO", response.getStatus());
        verify(comandaRepository, times(1)).findById(1L);
    }


    @Test
    void testGetAllComandas() {
        Comanda comanda1 = new Comanda();
        comanda1.setId(1L);
        comanda1.setNomeConsumidor("John Doe");
        comanda1.setStatus(Status.ABERTO);

        Item item1 = new Item();
        item1.setId(1L);
        item1.setNome("Pizza");
        item1.setCategoria(Categoria.PIZZA);
        comanda1.setItens(List.of(item1));

        Comanda comanda2 = new Comanda();
        comanda2.setId(2L);
        comanda2.setNomeConsumidor("Jane Smith");
        comanda2.setStatus(Status.FECHADA);

        Item item2 = new Item();
        item2.setId(2L);
        item2.setNome("Pasta");
        item2.setCategoria(Categoria.HAMBURGUER);
        comanda2.setItens(List.of(item2));

        when(comandaRepository.findAll()).thenReturn(List.of(comanda1, comanda2));

        List<ComandaResponseDTO> response = comandaService.getAllComandas();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(1L, response.get(0).getId());
        assertEquals("ABERTO", response.get(0).getStatus());
        assertEquals(2L, response.get(1).getId());
        assertEquals("FECHADA", response.get(1).getStatus());
        verify(comandaRepository, times(1)).findAll();
    }

}
