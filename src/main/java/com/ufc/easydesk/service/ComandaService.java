package com.ufc.easydesk.service;

import com.ufc.easydesk.api.http.request.ComandaRequestDTO;
import com.ufc.easydesk.api.http.request.ComandaStatusRequestDTO;
import com.ufc.easydesk.api.http.response.ComandaResponseDTO;
import com.ufc.easydesk.api.http.response.ItemResponseDTO;
import com.ufc.easydesk.domain.model.Comanda;
import com.ufc.easydesk.domain.model.Item;
import com.ufc.easydesk.domain.model.Mesa;
import com.ufc.easydesk.domain.enums.Status;
import com.ufc.easydesk.domain.repository.ComandaRepository;
import com.ufc.easydesk.domain.repository.ItemRepository;
import com.ufc.easydesk.domain.repository.MesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComandaService {

    private final ComandaRepository comandaRepository;
    private final MesaRepository mesaRepository;
    private final ItemRepository itemRepository;

    public ComandaResponseDTO createComanda(ComandaRequestDTO request) {
        Mesa mesa = mesaRepository.findById(request.getMesaId())
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada"));

        List<Item> itens = itemRepository.findAllById(request.getItens());

        Double total = itens.stream().mapToDouble(Item::getPreco).sum();
        total += request.getTaxaServico();

        Comanda comanda = Comanda.builder()
                .mesa(mesa)
                .numeroMesa(mesa.getNumeroMesa().intValue())  // Associar à mesa correta
                .nomeConsumidor(request.getNomeConsumidor())
                .observacao(request.getObservacao())
                .itens(itens)
                .taxaServico(request.getTaxaServico())
                .total(total)
                .ativa(true)
                .status(Status.ABERTA)  // Status inicial como ABERTA
                .dataHoraAbertura(LocalDateTime.now())  // Data e hora de abertura
                .build();

        Comanda savedComanda = comandaRepository.save(comanda);

        return convertToDto(savedComanda);
    }

    // Método para fechar uma comanda
    public ComandaResponseDTO fecharComanda(Long comandaId) {
        Comanda comanda = comandaRepository.findById(comandaId)
                .orElseThrow(() -> new RuntimeException("Comanda não encontrada"));

        if (!comanda.getAtiva()) {
            throw new RuntimeException("Comanda já está fechada.");
        }

        comanda.setAtiva(false);
        comanda.setStatus(Status.FECHADA);
        comanda.setDataHoraFechamento(LocalDateTime.now());

        Comanda savedComanda = comandaRepository.save(comanda);

        return convertToDto(savedComanda);
    }

    private ComandaResponseDTO convertToDto(Comanda comanda) {
        ComandaResponseDTO dto = new ComandaResponseDTO();
        dto.setId(comanda.getId());
        dto.setNumeroMesa(comanda.getNumeroMesa());
        dto.setNomeConsumidor(comanda.getNomeConsumidor());
        dto.setItens(comanda.getItens().stream()
                .map(item -> new ItemResponseDTO(item.getId(), item.getNome(), item.getDescricao(), item.getPreco(), item.getCategoria().name(), item.getDisponibilidade()))
                .collect(Collectors.toList()));
        dto.setTotal(comanda.getTotal());
        dto.setTaxaServico(comanda.getTaxaServico());
        dto.setAtiva(comanda.getAtiva());
        dto.setStatus(comanda.getStatus().name());
        dto.setDataHoraAbertura(comanda.getDataHoraAbertura());
        dto.setDataHoraFechamento(comanda.getDataHoraFechamento());
        return dto;
    }

    public ComandaResponseDTO atualizarStatus(Long comandaId, ComandaStatusRequestDTO request) {
        Comanda comanda = comandaRepository.findById(comandaId)
                .orElseThrow(() -> new RuntimeException("Comanda não encontrada"));

        comanda.setStatus(request.getStatus());

        // Se o status for FECHADA ou PAGA, marcar a comanda como inativa e registrar a data de fechamento
        if (request.getStatus() == Status.FECHADA || request.getStatus() == Status.PAGA) {
            comanda.setAtiva(false);
            comanda.setDataHoraFechamento(java.time.LocalDateTime.now());
        }

        Comanda updatedComanda = comandaRepository.save(comanda);

        return convertToDto(updatedComanda);
    }

    public ComandaResponseDTO getComandaById(Long comandaId) {
        Comanda comanda = comandaRepository.findById(comandaId)
                .orElseThrow(() -> new RuntimeException("Comanda não encontrada"));
        return convertToDto(comanda);
    }

    public List<ComandaResponseDTO> getComandasAtivas() {
        List<Comanda> comandasAtivas = comandaRepository.findByAtivaTrue();
        return comandasAtivas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

}
