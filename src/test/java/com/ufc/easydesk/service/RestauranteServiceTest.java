package com.ufc.easydesk.service;

import com.ufc.easydesk.api.http.request.RestauranteRequest;
import com.ufc.easydesk.api.http.response.RestauranteResponse;
import com.ufc.easydesk.model.Cliente;
import com.ufc.easydesk.model.Endereco;
import com.ufc.easydesk.model.Restaurante;
import com.ufc.easydesk.repository.ClienteRepository;
import com.ufc.easydesk.repository.RestauranteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestauranteServiceTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private RestauranteService restauranteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("test@example.com");
    }

    @Test
    void createRestaurante() {
        RestauranteRequest request = new RestauranteRequest();
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Endereço Teste");
        request.setNome("Restaurante Teste");
        request.setCnpj("12345678901234");
        request.setTelefone("123456789");
        request.setEndereco(endereco);

        Cliente cliente = new Cliente();
        cliente.setEmail("test@example.com");

        when(clienteRepository.findByEmail("test@example.com")).thenReturn(Optional.of(cliente));

        Restaurante restaurante = new Restaurante();
        restaurante.setNome(request.getNome());
        restaurante.setCnpj(request.getCnpj());
        restaurante.setTelefone(request.getTelefone());
        restaurante.setEndereco(request.getEndereco());
        restaurante.setProprietario(cliente);

        when(restauranteRepository.save(any(Restaurante.class))).thenReturn(restaurante);

        RestauranteResponse response = restauranteService.createRestaurante(request);

        assertNotNull(response);
        assertEquals("Restaurante Teste", response.getNome());
        assertEquals("12345678901234", response.getCnpj());
        assertEquals("123456789", response.getTelefone());
        assertEquals("Endereço Teste", response.getEndereco().getLogradouro());
        assertEquals("test@example.com", response.getProprietarioEmail());
    }

}