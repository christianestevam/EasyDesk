package com.ufc.easydesk.service;

import com.ufc.easydesk.api.http.request.CardapioRequestDTO;
import com.ufc.easydesk.api.http.request.ItemRequestDTO;
import com.ufc.easydesk.api.http.response.CardapioResponseDTO;
import com.ufc.easydesk.api.http.response.ItemResponseDTO;
import com.ufc.easydesk.domain.model.Cardapio;
import com.ufc.easydesk.domain.model.Item;
import com.ufc.easydesk.domain.model.Restaurante;
import com.ufc.easydesk.domain.enums.Categoria;
import com.ufc.easydesk.domain.repository.CardapioRepository;
import com.ufc.easydesk.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardapioService {

    private final CardapioRepository cardapioRepository;
    private final RestauranteRepository restauranteRepository;

    // Criar um cardápio
    public CardapioResponseDTO createCardapio(CardapioRequestDTO request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Restaurante restaurante = restauranteRepository.findById(request.getRestauranteId())
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        if (!restaurante.getProprietario().getEmail().equals(email)) {
            throw new RuntimeException("Você não tem permissão para criar um cardápio para este restaurante.");
        }

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

    // Filtrar itens por categoria em um cardápio específico
    public CardapioResponseDTO getCardapioByCategoria(Long restauranteId, Categoria categoria) {
        Cardapio cardapio = cardapioRepository.findByRestauranteId(restauranteId)
                .orElseThrow(() -> new RuntimeException("Cardápio não encontrado para o restaurante"));

        List<Item> itensFiltrados = cardapio.getItens().stream()
                .filter(item -> item.getCategoria().equals(categoria))
                .collect(Collectors.toList());

        return convertToDtoFiltrado(cardapio, itensFiltrados);
    }

    // Método para converter o cardápio filtrado para DTO
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

    // Converter entidade para DTO
    private CardapioResponseDTO convertToDto(Cardapio cardapio) {
        CardapioResponseDTO dto = new CardapioResponseDTO();
        dto.setId(cardapio.getId());
        dto.setItens(cardapio.getItens().stream()
                .map(item -> new ItemResponseDTO(
                        item.getId(), item.getNome(),
                        item.getDescricao(), item.getPreco(),
                        item.getCategoria().name(),
                        item.getDisponibilidade()))
                .collect(Collectors.toList()));
        return dto;
    }

    public CardapioResponseDTO getCardapioCompleto(Long restauranteId) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        Cardapio cardapio = cardapioRepository.findByRestauranteId(restauranteId)
                .orElseThrow(() -> new RuntimeException("Cardápio não encontrado para o restaurante"));

        return convertToDto(cardapio);
    }

    // Conversão do cardápio para DTO

    public CardapioResponseDTO addItemsToCardapio(Long cardapioId, List<ItemRequestDTO> itensRequest) {
        Cardapio cardapio = cardapioRepository.findById(cardapioId)
                .orElseThrow(() -> new RuntimeException("Cardápio não encontrado"));

        List<Item> novosItens = itensRequest.stream()
                .map(itemRequest -> new Item(
                        null, itemRequest.getNome(),
                        itemRequest.getDescricao(),
                        itemRequest.getPreco(),
                        itemRequest.getCategoria(),
                        itemRequest.getDisponibilidade()))
                .collect(Collectors.toList());

        cardapio.getItens().addAll(novosItens);
        Cardapio updatedCardapio = cardapioRepository.save(cardapio);

        return convertToDto(updatedCardapio);
    }

    public ItemResponseDTO updateItem(Long cardapioId, Long itemId, ItemRequestDTO itemRequest) {
        Cardapio cardapio = cardapioRepository.findById(cardapioId)
                .orElseThrow(() -> new RuntimeException("Cardápio não encontrado"));

        Item item = cardapio.getItens().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item não encontrado no cardápio"));

        item.setNome(itemRequest.getNome());
        item.setDescricao(itemRequest.getDescricao());
        item.setPreco(itemRequest.getPreco());
        item.setCategoria(itemRequest.getCategoria());
        item.setDisponibilidade(itemRequest.getDisponibilidade());

        cardapioRepository.save(cardapio);

        return new ItemResponseDTO(item.getId(), item.getNome(), item.getDescricao(), item.getPreco(), item.getCategoria().name(), item.getDisponibilidade());
    }

    public void deleteItem(Long cardapioId, Long itemId) {
        Cardapio cardapio = cardapioRepository.findById(cardapioId)
                .orElseThrow(() -> new RuntimeException("Cardápio não encontrado"));

        Item itemToRemove = cardapio.getItens().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item não encontrado no cardápio"));

        cardapio.getItens().remove(itemToRemove);
        cardapioRepository.save(cardapio);
    }

    public CardapioResponseDTO updateCardapio(Long cardapioId, CardapioRequestDTO request) {
        Cardapio cardapio = cardapioRepository.findById(cardapioId)
                .orElseThrow(() -> new RuntimeException("Cardápio não encontrado"));

        Restaurante restaurante = restauranteRepository.findById(request.getRestauranteId())
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        // Verifica se o usuário tem permissão para editar o cardápio desse restaurante
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!restaurante.getProprietario().getEmail().equals(email)) {
            throw new RuntimeException("Você não tem permissão para editar o cardápio deste restaurante.");
        }

        // Atualizar o restaurante associado ao cardápio
        cardapio.setRestaurante(restaurante);

        // Atualizar os itens do cardápio
        List<Item> updatedItens = request.getItens().stream()
                .map(itemRequest -> new Item(
                        null, itemRequest.getNome(),
                        itemRequest.getDescricao(),
                        itemRequest.getPreco(),
                        itemRequest.getCategoria(),
                        itemRequest.getDisponibilidade()))
                .collect(Collectors.toList());

        cardapio.setItens(updatedItens);

        // Salvar alterações
        Cardapio updatedCardapio = cardapioRepository.save(cardapio);

        // Retornar o cardápio atualizado
        return convertToDto(updatedCardapio);
    }





}
