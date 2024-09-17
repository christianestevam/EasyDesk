package com.ufc.easydesk.service;

import com.ufc.easydesk.api.http.request.RegisterRequest;
import com.ufc.easydesk.domain.model.Cliente;
import com.ufc.easydesk.domain.model.Role;
import com.ufc.easydesk.domain.enums.RoleName;
import com.ufc.easydesk.domain.repository.ClienteRepository;
import com.ufc.easydesk.domain.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setNome("John Doe");
        registerRequest.setEmail("johndoe@example.com");
        registerRequest.setSenha("password123");
        registerRequest.setCnpjCpf("12345678900");
        registerRequest.setTelefone("123456789");

        Role role = new Role();
        role.setNome(RoleName.ROLE_ADMIN);

        when(roleRepository.findByNome(RoleName.ROLE_ADMIN)).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(registerRequest.getSenha())).thenReturn("encodedPassword");

        authService.register(registerRequest);

        verify(clienteRepository, times(1)).save(any(Cliente.class));
        verify(passwordEncoder, times(1)).encode("password123");
        verify(roleRepository, times(1)).findByNome(RoleName.ROLE_ADMIN);
    }
}
