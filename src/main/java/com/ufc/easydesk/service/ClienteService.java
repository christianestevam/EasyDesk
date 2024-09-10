package com.ufc.easydesk.service;

import com.ufc.easydesk.api.http.response.ClienteResponseDTO;
import com.ufc.easydesk.api.http.response.EnderecoResponseDTO;
import com.ufc.easydesk.api.http.response.RestauranteResponseDTO;
import com.ufc.easydesk.domain.model.Cliente;
import com.ufc.easydesk.domain.model.Restaurante;
import com.ufc.easydesk.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteResponseDTO getClienteLogado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        return convertToDTO(cliente);
    }

    private ClienteResponseDTO convertToDTO(Cliente cliente) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(cliente.getId());
        dto.setEmail(cliente.getEmail());
        dto.setNome(cliente.getNome());
        dto.setCnpjCpf(cliente.getCnpjCpf());
        dto.setTelefone(cliente.getTelefone());

        List<RestauranteResponseDTO> restaurantesDTO = cliente.getRestaurantes().stream()
                .map(this::convertRestauranteToDTO)
                .collect(Collectors.toList());
        dto.setRestaurantes(restaurantesDTO);

        return dto;
    }

    private RestauranteResponseDTO convertRestauranteToDTO(Restaurante restaurante) {
        RestauranteResponseDTO dto = new RestauranteResponseDTO();
        dto.setId(restaurante.getId());
        dto.setNome(restaurante.getNome());
        dto.setTelefone(restaurante.getTelefone());
        dto.setCnpj(restaurante.getCnpj());

        EnderecoResponseDTO enderecoDTO = new EnderecoResponseDTO();
        enderecoDTO.setLogradouro(restaurante.getEndereco().getLogradouro());
        enderecoDTO.setNumero(restaurante.getEndereco().getNumero());
        enderecoDTO.setComplemento(restaurante.getEndereco().getComplemento());
        enderecoDTO.setBairro(restaurante.getEndereco().getBairro());
        enderecoDTO.setCidade(restaurante.getEndereco().getCidade());
        enderecoDTO.setEstado(restaurante.getEndereco().getEstado());
        enderecoDTO.setCep(restaurante.getEndereco().getCep());
        dto.setEndereco(enderecoDTO);

        return dto;
    }
}
