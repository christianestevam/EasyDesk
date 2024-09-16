package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.request.FuncionarioRequest;
import com.ufc.easydesk.service.FuncionarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/funcionario")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<?> createFuncionario(@Valid @RequestBody FuncionarioRequest request) {
        funcionarioService.createFuncionario(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
