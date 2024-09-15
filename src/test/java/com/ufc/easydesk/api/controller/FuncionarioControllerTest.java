package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.request.FuncionarioRequest;
import com.ufc.easydesk.service.FuncionarioService;
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

public class FuncionarioControllerTest {

    @Mock
    private FuncionarioService funcionarioService;

    @InjectMocks
    private FuncionarioController funcionarioController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createFuncionario() {
        FuncionarioRequest funcionarioRequest = new FuncionarioRequest();

        when(funcionarioService.createFuncionario(any(FuncionarioRequest.class))).thenReturn(null);

        ResponseEntity<?> response = funcionarioController.createFuncionario(funcionarioRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}