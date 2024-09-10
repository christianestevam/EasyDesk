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

    // Criar mesa associada a um restaurante
    public MesaResponseDTO createMesa(Mesa mesa, Long restauranteId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        // Verifica se o restaurante pertence ao cliente autenticado
        if (!restaurante.getProprietario().getEmail().equals(email)) {
            throw new RuntimeException("Você não tem permissão para acessar este restaurante.");
        }

        mesa.setRestaurante(restaurante);
        Mesa savedMesa = mesaRepository.save(mesa);

        return convertToDto(savedMesa);
    }

    // Buscar todas as mesas de um restaurante específico
    public List<MesaResponseDTO> getMesasByRestaurante(Long restauranteId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        // Verifica se o restaurante pertence ao cliente autenticado
        if (!restaurante.getProprietario().getEmail().equals(email)) {
            throw new RuntimeException("Você não tem permissão para acessar este restaurante.");
        }

        return mesaRepository.findByRestauranteId(restauranteId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Método para converter a entidade Mesa para MesaResponseDTO
    private MesaResponseDTO convertToDto(Mesa mesa) {
        MesaResponseDTO dto = new MesaResponseDTO();
        dto.setNumeroMesa(mesa.getNumeroMesa());
        dto.setDisponibilidade(mesa.getDisponibilidade());
        return dto;
    }
}
