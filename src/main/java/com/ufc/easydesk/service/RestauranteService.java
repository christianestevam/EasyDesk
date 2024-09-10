package com.ufc.easydesk.service;

import com.ufc.easydesk.api.http.request.RestauranteRequest;
import com.ufc.easydesk.api.http.response.RestauranteResponse;
import com.ufc.easydesk.domain.model.Cliente;
import com.ufc.easydesk.domain.model.Restaurante;
import com.ufc.easydesk.domain.repository.ClienteRepository;
import com.ufc.easydesk.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final ClienteRepository clienteRepository;

    public RestauranteResponse createRestaurante(RestauranteRequest request) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Cliente proprietario = clienteRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Proprietário não encontrado"));

        Restaurante restaurante = new Restaurante();
        restaurante.setNome(request.getNome());
        restaurante.setCnpj(request.getCnpj());
        restaurante.setTelefone(request.getTelefone());
        restaurante.setEndereco(request.getEndereco());
        restaurante.setProprietario(proprietario);

        Restaurante savedRestaurante = restauranteRepository.save(restaurante);

        RestauranteResponse response = new RestauranteResponse();
        response.setId(savedRestaurante.getId());
        response.setNome(savedRestaurante.getNome());
        response.setCnpj(savedRestaurante.getCnpj());
        response.setTelefone(savedRestaurante.getTelefone());
        response.setEndereco(savedRestaurante.getEndereco());
        response.setProprietarioEmail(proprietario.getEmail());

        return response;
    }
}
