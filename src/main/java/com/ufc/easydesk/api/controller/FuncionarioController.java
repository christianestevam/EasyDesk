package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.request.FuncionarioRequest;
import com.ufc.easydesk.service.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Funcionários", description = "Gerenciamento de Funcionários")
@RestController
@RequestMapping("/api/funcionario")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @Operation(summary = "Criar Funcionário", description = "Cria um novo funcionário para o restaurante.")
    @ApiResponse(responseCode = "201", description = "Funcionário criado com sucesso.")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<?> createFuncionario(@Valid @RequestBody FuncionarioRequest request) {
        funcionarioService.createFuncionario(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
