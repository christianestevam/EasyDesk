package com.ufc.easydesk.service;

import com.ufc.easydesk.api.http.request.AuthenticationRequest;
import com.ufc.easydesk.api.http.request.RegisterRequest;
import com.ufc.easydesk.api.http.response.AuthenticationResponse;
import com.ufc.easydesk.model.Cliente;
import com.ufc.easydesk.model.Role;
import com.ufc.easydesk.model.enums.RoleName;
import com.ufc.easydesk.repository.ClienteRepository;
import com.ufc.easydesk.repository.RoleRepository;
import com.ufc.easydesk.config.jwt.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private MyUserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setNome("Test User");
        registerRequest.setCnpjCpf("123456789");
        registerRequest.setTelefone("123456789");
        registerRequest.setEmail("test@example.com");
        registerRequest.setSenha("password");

        Role role = new Role();
        role.setNome(RoleName.ROLE_ADMIN);

        when(roleRepository.findByNome(RoleName.ROLE_ADMIN)).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

        authService.register(registerRequest);

        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void authenticate() throws Exception {
        AuthenticationRequest authenticationRequest = mock(AuthenticationRequest.class);
        when(authenticationRequest.getUsername()).thenReturn("testuser");
        when(authenticationRequest.getPassword()).thenReturn("password");

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("testuser");
        when(userDetails.getAuthorities()).thenReturn(Collections.emptyList());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);
        when(jwtUtil.generateToken("testuser", Collections.emptyList())).thenReturn("jwtToken");

        AuthenticationResponse response = authService.authenticate(authenticationRequest);

        assertEquals("jwtToken", response.getJwt());
    }

    @Test
    void authenticateThrowsException() {
        AuthenticationRequest authenticationRequest = mock(AuthenticationRequest.class);
        when(authenticationRequest.getUsername()).thenReturn("testuser");
        when(authenticationRequest.getPassword()).thenReturn("wrongpassword");

        doThrow(new BadCredentialsException("Incorrect username or password"))
                .when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThrows(BadCredentialsException.class, () -> authService.authenticate(authenticationRequest));
    }
}