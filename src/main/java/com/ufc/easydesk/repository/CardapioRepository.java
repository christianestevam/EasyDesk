package com.ufc.easydesk.repository;

import com.ufc.easydesk.model.Cardapio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardapioRepository extends JpaRepository<Cardapio, Long> {
    Optional<Cardapio> findByRestauranteId(Long restauranteId);
}
