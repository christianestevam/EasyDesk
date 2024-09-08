package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.request.RestauranteRequest;
import com.ufc.easydesk.api.http.response.RestauranteResponse;
import com.ufc.easydesk.service.RestauranteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/restaurantes")
@RequiredArgsConstructor
public class RestauranteController {

    private final RestauranteService restauranteService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<RestauranteResponse> createRestaurant(@Valid @RequestBody RestauranteRequest request) {
        RestauranteResponse response = restauranteService.createRestaurante(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
