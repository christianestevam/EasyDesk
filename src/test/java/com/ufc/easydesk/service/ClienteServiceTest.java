package com.ufc.easydesk.service;

import com.ufc.easydesk.api.http.response.ClienteResponseDTO;
import com.ufc.easydesk.api.http.response.EnderecoResponseDTO;
import com.ufc.easydesk.api.http.response.RestauranteResponseDTO;
import com.ufc.easydesk.domain.model.Cliente;
import com.ufc.easydesk.domain.model.Endereco;
import com.ufc.easydesk.domain.model.Restaurante;
import com.ufc.easydesk.domain.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testGetClienteLogado_Success() {
        // Mockando o contexto de segurança
        String email = "cliente@example.com";
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
        SecurityContextHolder.setContext(securityContext);

        // Mockando a busca do cliente
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setEmail(email);
        cliente.setNome("Cliente Teste");
        cliente.setCnpjCpf("123456789");
        cliente.setTelefone("999999999");

        // Mockando o restaurante do cliente
        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);
        restaurante.setNome("Restaurante Teste");
        restaurante.setTelefone("999999999");
        restaurante.setCnpj("12345678901234");

        // Mockando o endereço do restaurante
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua Teste");
        endereco.setNumero("100");
        endereco.setComplemento("Apt 101");
        endereco.setBairro("Bairro Teste");
        endereco.setCidade("Cidade Teste");
        endereco.setEstado("CE");
        endereco.setCep("12345678");

        restaurante.setEndereco(endereco);
        cliente.setRestaurante(restaurante);

        when(clienteRepository.findByEmail(email)).thenReturn(Optional.of(cliente));

        // Executando o serviço
        ClienteResponseDTO response = clienteService.getClienteLogado();

        // Verificações
        assertNotNull(response);
        assertEquals(cliente.getId(), response.getId());
        assertEquals(cliente.getEmail(), response.getEmail());
        assertEquals(cliente.getNome(), response.getNome());
        assertEquals(cliente.getCnpjCpf(), response.getCnpjCpf());
        assertEquals(cliente.getTelefone(), response.getTelefone());

        RestauranteResponseDTO restauranteDTO = response.getRestaurante();
        assertNotNull(restauranteDTO);
        assertEquals(restaurante.getId(), restauranteDTO.getId());
        assertEquals(restaurante.getNome(), restauranteDTO.getNome());
        assertEquals(restaurante.getCnpj(), restauranteDTO.getCnpj());
        assertEquals(restaurante.getTelefone(), restauranteDTO.getTelefone());

        EnderecoResponseDTO enderecoDTO = restauranteDTO.getEndereco();
        assertNotNull(enderecoDTO);
        assertEquals(endereco.getLogradouro(), enderecoDTO.getLogradouro());
        assertEquals(endereco.getNumero(), enderecoDTO.getNumero());
        assertEquals(endereco.getComplemento(), enderecoDTO.getComplemento());
        assertEquals(endereco.getBairro(), enderecoDTO.getBairro());
        assertEquals(endereco.getCidade(), enderecoDTO.getCidade());
        assertEquals(endereco.getEstado(), enderecoDTO.getEstado());
        assertEquals(endereco.getCep(), enderecoDTO.getCep());
    }

    @Test
    void testGetClienteLogado_NotFound() {
        // Mockando o contexto de segurança
        String email = "cliente_nao_existente@example.com";
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
        SecurityContextHolder.setContext(securityContext);

        // Mockando a busca do cliente
        when(clienteRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Executando o serviço e verificando exceção
        Exception exception = assertThrows(RuntimeException.class, () -> {
            clienteService.getClienteLogado();
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
    }
}
