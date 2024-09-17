package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.request.AuthenticationRequest;
import com.ufc.easydesk.api.http.request.RegisterRequest;
import com.ufc.easydesk.api.http.response.AuthenticationResponse;
import com.ufc.easydesk.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Registrar novo usuário", description = "Permite registrar um novo usuário no sistema.")
    @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso")
    @PostMapping("/registro")
    public ResponseEntity<Void> registerUser(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Autenticar usuário", description = "Permite que um usuário faça login no sistema.")
    @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso")
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        AuthenticationResponse response = authService.authenticate(authenticationRequest);
        return ResponseEntity.ok(response);
    }

}
