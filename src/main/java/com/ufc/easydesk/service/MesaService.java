package com.ufc.easydesk.service;

import com.ufc.easydesk.api.http.response.MesaResponseDTO;
import com.ufc.easydesk.domain.model.Mesa;
import com.ufc.easydesk.domain.model.Restaurante;
import com.ufc.easydesk.domain.repository.MesaRepository;
import com.ufc.easydesk.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MesaService {

    private final MesaRepository mesaRepository;
    private final RestauranteRepository restauranteRepository;

    // Criar mesa associada ao restaurante do cliente logado
    public MesaResponseDTO createMesa(Mesa mesa) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Restaurante restaurante = restauranteRepository.findByProprietarioEmail(email)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado para o cliente logado"));

        mesa.setRestaurante(restaurante);
        Mesa savedMesa = mesaRepository.save(mesa);

        return convertToDto(savedMesa);
    }

    // Alterar a disponibilidade da mesa
    public MesaResponseDTO alterarDisponibilidadeMesa(Long mesaId, Boolean disponibilidade) {
        Mesa mesa = mesaRepository.findById(mesaId)
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada"));

        mesa.setDisponibilidade(disponibilidade);
        Mesa updatedMesa = mesaRepository.save(mesa);

        return convertToDto(updatedMesa);
    }

    // Buscar todas as mesas do restaurante do cliente logado
    public List<MesaResponseDTO> getMesasByRestaurante() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Restaurante restaurante = restauranteRepository.findByProprietarioEmail(email)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado para o cliente logado"));

        return mesaRepository.findByRestauranteId(restaurante.getId()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Método para converter a entidade Mesa para MesaResponseDTO
    private MesaResponseDTO convertToDto(Mesa mesa) {
        MesaResponseDTO dto = new MesaResponseDTO();
        dto.setId(mesa.getId());  // Retorna o ID da mesa
        dto.setNumeroMesa(mesa.getNumeroMesa());
        dto.setDisponibilidade(mesa.getDisponibilidade());
        return dto;
    }

    public void deleteMesa(Long mesaId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Mesa mesa = mesaRepository.findById(mesaId)
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada"));

        // Verifica se o restaurante da mesa pertence ao usuário logado
        if (!mesa.getRestaurante().getProprietario().getEmail().equals(email)) {
            throw new RuntimeException("Você não tem permissão para deletar esta mesa.");
        }

        mesaRepository.delete(mesa);
    }
}
