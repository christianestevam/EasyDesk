package com.ufc.easydesk.domain.repository;

import com.ufc.easydesk.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    Optional<Restaurante> findByProprietarioEmail(String email);
}
