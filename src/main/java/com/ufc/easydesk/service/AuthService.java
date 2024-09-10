package com.ufc.easydesk.service;


import com.ufc.easydesk.api.http.request.AuthenticationRequest;
import com.ufc.easydesk.api.http.response.AuthenticationResponse;
import com.ufc.easydesk.api.http.request.RegisterRequest;
import com.ufc.easydesk.domain.model.Cliente;
import com.ufc.easydesk.domain.model.Role;
import com.ufc.easydesk.domain.enums.RoleName;
import com.ufc.easydesk.domain.repository.ClienteRepository;
import com.ufc.easydesk.config.jwt.JwtUtil;
import com.ufc.easydesk.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final ClienteRepository clienteRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final MyUserDetailsService userDetailsService;

    private final RoleRepository roleRepository;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Registra um novo cliente no sistema.
     *
     * @param registerRequest Objeto com as informações de registro.
     * @return O cliente registrado.
     */
    public void register(RegisterRequest registerRequest) {
        Cliente cliente = new Cliente();
        cliente.setNome(registerRequest.getNome());
        cliente.setCnpjCpf(registerRequest.getCnpjCpf());
        cliente.setTelefone(registerRequest.getTelefone());
        cliente.setEmail(registerRequest.getEmail());
        cliente.setSenha(passwordEncoder.encode(registerRequest.getSenha()));

        Role role = roleRepository.findByNome(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        cliente.setRoles(Collections.singleton(role));

        clienteRepository.save(cliente);
    }


    /**
     * Autentica um usuário e gera um token JWT.
     *
     * @param authenticationRequest Objeto com as credenciais de autenticação.
     * @return Um objeto contendo o token JWT.
     * @throws Exception Se o usuário ou senha estiverem incorretos.
     */
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername(), userDetails.getAuthorities());

        return new AuthenticationResponse(jwt);
    }

}
