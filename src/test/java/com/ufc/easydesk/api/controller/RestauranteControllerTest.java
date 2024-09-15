package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.request.RestauranteRequest;
import com.ufc.easydesk.api.http.response.RestauranteResponse;
import com.ufc.easydesk.service.RestauranteService;
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

class RestauranteControllerTest {

    @Mock
    private RestauranteService restauranteService;

    @InjectMocks
    private RestauranteController restauranteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRestaurant() {
        RestauranteRequest restauranteRequest = new RestauranteRequest();
        RestauranteResponse restauranteResponse = new RestauranteResponse();

        when(restauranteService.createRestaurante(any(RestauranteRequest.class))).thenReturn(restauranteResponse);

        ResponseEntity<RestauranteResponse> response = restauranteController.createRestaurant(restauranteRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(restauranteResponse, response.getBody());
    }
}