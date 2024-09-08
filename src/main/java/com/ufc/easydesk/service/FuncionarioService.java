package com.ufc.easydesk.service;

import com.ufc.easydesk.api.http.request.FuncionarioRequest;
import com.ufc.easydesk.model.Funcionario;
import com.ufc.easydesk.model.Restaurante;
import com.ufc.easydesk.model.Role;
import com.ufc.easydesk.model.enums.RoleName;
import com.ufc.easydesk.repository.FuncionarioRepository;
import com.ufc.easydesk.repository.RestauranteRepository;
import com.ufc.easydesk.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final RestauranteRepository restauranteRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Funcionario createFuncionario(FuncionarioRequest request) {
        Restaurante restaurante = restauranteRepository.findById(request.getRestauranteId())
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        Role role = roleRepository.findByNome(RoleName.valueOf(request.getRole()))
                .orElseThrow(() -> new RuntimeException("Role não encontrada"));

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(request.getNome());
        funcionario.setEmail(request.getEmail());
        funcionario.setSenha(passwordEncoder.encode(request.getSenha()));
        funcionario.setRestaurante(restaurante);
        funcionario.setRoles(Collections.singleton(role));

        return funcionarioRepository.save(funcionario);
    }
}
