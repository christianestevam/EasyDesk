package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.request.AuthenticationRequest;
import com.ufc.easydesk.api.http.request.RegisterRequest;
import com.ufc.easydesk.api.http.response.AuthenticationResponse;
import com.ufc.easydesk.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        AuthenticationResponse response = authService.authenticate(authenticationRequest);
        return ResponseEntity.ok(response);
    }
}
