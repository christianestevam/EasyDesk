package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.request.AuthenticationRequest;
import com.ufc.easydesk.api.http.request.RegisterRequest;
import com.ufc.easydesk.api.http.response.AuthenticationResponse;
import com.ufc.easydesk.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser() {
        RegisterRequest registerRequest = new RegisterRequest();
        ResponseEntity<Void> response = authController.registerUser(registerRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testCreateAuthenticationToken() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        AuthenticationResponse authenticationResponse = new AuthenticationResponse("testToken");

        when(authService.authenticate(any(AuthenticationRequest.class))).thenReturn(authenticationResponse);

        ResponseEntity<?> response = authController.createAuthenticationToken(authenticationRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authenticationResponse, response.getBody());
    }
}