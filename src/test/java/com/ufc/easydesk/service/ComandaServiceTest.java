package com.ufc.easydesk.service;

import com.ufc.easydesk.api.http.request.ComandaRequestDTO;
import com.ufc.easydesk.api.http.request.ComandaStatusRequestDTO;
import com.ufc.easydesk.api.http.response.ComandaResponseDTO;
import com.ufc.easydesk.model.Comanda;
import com.ufc.easydesk.model.Item;
import com.ufc.easydesk.model.Mesa;
import com.ufc.easydesk.model.enums.Categoria;
import com.ufc.easydesk.model.enums.Status;
import com.ufc.easydesk.repository.ComandaRepository;
import com.ufc.easydesk.repository.ItemRepository;
import com.ufc.easydesk.repository.MesaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createComanda() {
        ComandaRequestDTO request = new ComandaRequestDTO();
        request.setMesaId(1L);
        request.setItens(List.of(1L, 2L));
        request.setTaxaServico(10.0);
        request.setNomeConsumidor("João Mario");
        request.setObservacao("Sem cebola");

        Mesa mesa = new Mesa();
        mesa.setId(1L);
        mesa.setNumeroMesa(5L);

        Item item1 = new Item();
        item1.setId(1L);
        item1.setPreco(20.0);
        item1.setCategoria(Categoria.BEBIDA);

        Item item2 = new Item();
        item2.setId(2L);
        item2.setPreco(30.0);
        item2.setCategoria(Categoria.HAMBURGUER);

        when(mesaRepository.findById(1L)).thenReturn(Optional.of(mesa));
        when(itemRepository.findAllById(request.getItens())).thenReturn(List.of(item1, item2));

        Comanda comanda = Comanda.builder()
                .mesa(mesa)
                .numeroMesa(mesa.getNumeroMesa().intValue())
                .nomeConsumidor(request.getNomeConsumidor())
                .observacao(request.getObservacao())
                .itens(new ArrayList<>(List.of(item1, item2)))
                .taxaServico(request.getTaxaServico())
                .total(60.0)
                .ativa(true)
                .status(Status.ABERTA)
                .dataHoraAbertura(LocalDateTime.now())
                .build();

        when(comandaRepository.save(any(Comanda.class))).thenReturn(comanda);

        ComandaResponseDTO response = comandaService.createComanda(request);

        assertNotNull(response);
        verify(comandaRepository, times(1)).save(any(Comanda.class));
    }

    @Test
    void fecharComanda() {
        Comanda comanda = new Comanda();
        comanda.setId(1L);
        comanda.setAtiva(true);
        comanda.setStatus(Status.ABERTA);
        comanda.setItens(new ArrayList<>()); // Initialize itens as an empty list

        when(comandaRepository.findById(1L)).thenReturn(Optional.of(comanda));
        when(comandaRepository.save(any(Comanda.class))).thenReturn(comanda);

        ComandaResponseDTO response = comandaService.fecharComanda(1L);

        assertNotNull(response);
        assertFalse(response.getAtiva());
        assertEquals(Status.FECHADA.name(), response.getStatus());
        verify(comandaRepository, times(1)).save(any(Comanda.class));
    }

    @Test
    void atualizarStatus() {
        Comanda comanda = new Comanda();
        comanda.setId(1L);
        comanda.setStatus(Status.ABERTA);
        comanda.setItens(new ArrayList<>());

        ComandaStatusRequestDTO request = new ComandaStatusRequestDTO();
        request.setStatus(Status.PAGA);

        when(comandaRepository.findById(1L)).thenReturn(Optional.of(comanda));
        when(comandaRepository.save(any(Comanda.class))).thenReturn(comanda);

        ComandaResponseDTO response = comandaService.atualizarStatus(1L, request);

        assertNotNull(response);
        assertEquals(Status.PAGA.name(), response.getStatus());
        verify(comandaRepository, times(1)).save(any(Comanda.class));
    }

    @Test
    void getComandaById() {
        Comanda comanda = new Comanda();
        comanda.setId(1L);
        comanda.setItens(new ArrayList<>());
        comanda.setStatus(Status.ABERTA);

        when(comandaRepository.findById(1L)).thenReturn(Optional.of(comanda));

        ComandaResponseDTO response = comandaService.getComandaById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        verify(comandaRepository, times(1)).findById(1L);
    }
}