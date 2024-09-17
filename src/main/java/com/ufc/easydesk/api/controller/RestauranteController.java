package com.ufc.easydesk.api.controller;

import com.ufc.easydesk.api.http.request.RestauranteRequest;
import com.ufc.easydesk.api.http.response.RestauranteResponse;
import com.ufc.easydesk.service.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Restaurantes", description = "Gerenciamento de Restaurantes")
@RestController
@RequestMapping("/api/restaurante")
@RequiredArgsConstructor
public class RestauranteController {

    private final RestauranteService restauranteService;

    @Operation(summary = "Criar Restaurante", description = "Cria um novo restaurante.")
    @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso.")
    @PostMapping
    public ResponseEntity<RestauranteResponse> createRestaurant(@Valid @RequestBody RestauranteRequest request) {
        RestauranteResponse response = restauranteService.createRestaurante(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
