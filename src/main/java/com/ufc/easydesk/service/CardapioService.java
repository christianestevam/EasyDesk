package com.ufc.easydesk.service;

import com.ufc.easydesk.api.http.request.CardapioRequestDTO;
import com.ufc.easydesk.api.http.request.ItemRequestDTO;
import com.ufc.easydesk.api.http.response.CardapioResponseDTO;
import com.ufc.easydesk.api.http.response.ItemResponseDTO;
import com.ufc.easydesk.domain.enums.Categoria;
import com.ufc.easydesk.domain.model.Cardapio;
import com.ufc.easydesk.domain.model.Cliente;
import com.ufc.easydesk.domain.model.Item;
import com.ufc.easydesk.domain.model.Restaurante;
import com.ufc.easydesk.domain.repository.CardapioRepository;
import com.ufc.easydesk.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardapioService {

    private final CardapioRepository cardapioRepository;
    private final ClienteRepository clienteRepository;

    // Criar um cardápio
    public CardapioResponseDTO createCardapio(CardapioRequestDTO request) {
        Cliente cliente = getClienteLogado();
        Restaurante restaurante = cliente.getRestaurante();  // Obter restaurante do cliente logado

        Cardapio cardapio = new Cardapio();
        cardapio.setRestaurante(restaurante);
        cardapio.setItens(request.getItens().stream()
                .map(itemRequest -> new Item(
                        null, itemRequest.getNome(),
                        itemRequest.getDescricao(),
                        itemRequest.getPreco(),
                        itemRequest.getCategoria(),
                        itemRequest.getDisponibilidade()))
                .collect(Collectors.toList()));

        Cardapio savedCardapio = cardapioRepository.save(cardapio);

        return convertToDto(savedCardapio);
    }

    // Filtrar itens por categoria no cardápio do cliente logado
    public CardapioResponseDTO getCardapioByCategoria(Categoria categoria) {
        Cliente cliente = getClienteLogado();
        Restaurante restaurante = cliente.getRestaurante();  // Obter restaurante do cliente logado

        Cardapio cardapio = cardapioRepository.findByRestauranteId(restaurante.getId())
                .orElseThrow(() -> new RuntimeException("Cardápio não encontrado para o restaurante"));

        List<Item> itensFiltrados = cardapio.getItens().stream()
                .filter(item -> item.getCategoria().equals(categoria))
                .collect(Collectors.toList());

        return convertToDtoFiltrado(cardapio, itensFiltrados);
    }

    // Obter o cardápio completo do restaurante do cliente logado
    public CardapioResponseDTO getCardapioCompleto() {
        Cliente cliente = getClienteLogado();
        Restaurante restaurante = cliente.getRestaurante();  // Obter restaurante do cliente logado

        Cardapio cardapio = cardapioRepository.findByRestauranteId(restaurante.getId())
                .orElseThrow(() -> new RuntimeException("Cardápio não encontrado para o restaurante"));

        return convertToDto(cardapio);
    }

    // Método para obter cliente logado
    private Cliente getClienteLogado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return clienteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    // Métodos de conversão
    private CardapioResponseDTO convertToDto(Cardapio cardapio) {
        CardapioResponseDTO dto = new CardapioResponseDTO();
        dto.setId(cardapio.getId());
        dto.setItens(cardapio.getItens().stream()
                .map(item -> new ItemResponseDTO(
                        item.getId(),
                        item.getNome(),
                        item.getDescricao(),
                        item.getPreco(),
                        item.getCategoria() != null ? item.getCategoria().name() : "SEM_CATEGORIA",
                        item.getDisponibilidade()))
                .collect(Collectors.toList()));
        return dto;
    }

    private CardapioResponseDTO convertToDtoFiltrado(Cardapio cardapio, List<Item> itensFiltrados) {
        CardapioResponseDTO dto = new CardapioResponseDTO();
        dto.setId(cardapio.getId());
        dto.setItens(itensFiltrados.stream()
                .map(item -> new ItemResponseDTO(
                        item.getId(), item.getNome(),
                        item.getDescricao(), item.getPreco(),
                        item.getCategoria().name(),
                        item.getDisponibilidade()))
                .collect(Collectors.toList()));
        return dto;
    }

    public CardapioResponseDTO updateCardapio(Long cardapioId, CardapioRequestDTO request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Cardapio cardapio = cardapioRepository.findById(cardapioId)
                .orElseThrow(() -> new RuntimeException("Cardápio não encontrado"));

        // Verificar se o restaurante do cardápio pertence ao usuário logado
        if (!cardapio.getRestaurante().getProprietario().getEmail().equals(email)) {
            throw new RuntimeException("Você não tem permissão para alterar este cardápio.");
        }

        // Atualizar os itens do cardápio
        cardapio.getItens().clear();
        cardapio.setItens(request.getItens().stream()
                .map(itemRequest -> new Item(
                        null,
                        itemRequest.getNome(),
                        itemRequest.getDescricao(),
                        itemRequest.getPreco(),
                        itemRequest.getCategoria(),
                        itemRequest.getDisponibilidade()))
                .collect(Collectors.toList()));

        Cardapio updatedCardapio = cardapioRepository.save(cardapio);

        return convertToDto(updatedCardapio);
    }
}
