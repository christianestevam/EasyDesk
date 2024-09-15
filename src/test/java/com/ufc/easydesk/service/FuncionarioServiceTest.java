package com.ufc.easydesk.service;

import com.ufc.easydesk.api.http.request.FuncionarioRequest;
import com.ufc.easydesk.model.Funcionario;
import com.ufc.easydesk.model.Restaurante;
import com.ufc.easydesk.model.Role;
import com.ufc.easydesk.model.enums.RoleName;
import com.ufc.easydesk.repository.FuncionarioRepository;
import com.ufc.easydesk.repository.RestauranteRepository;
import com.ufc.easydesk.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private FuncionarioService funcionarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createFuncionario() {
        FuncionarioRequest request = new FuncionarioRequest();
        request.setNome("Test Funcionario");
        request.setEmail("test@example.com");
        request.setSenha("password");
        request.setRestauranteId(1L);
        request.setRole("ROLE_ADMIN");

        Restaurante restaurante = new Restaurante();
        restaurante.setId(1L);

        Role role = new Role();
        role.setNome(RoleName.ROLE_ADMIN);

        when(restauranteRepository.findById(1L)).thenReturn(Optional.of(restaurante));
        when(roleRepository.findByNome(RoleName.ROLE_ADMIN)).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(new Funcionario());

        Funcionario funcionario = funcionarioService.createFuncionario(request);

        assertNotNull(funcionario);
        verify(funcionarioRepository, times(1)).save(any(Funcionario.class));
    }
}