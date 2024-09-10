package com.ufc.easydesk.domain.repository;

import com.ufc.easydesk.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
}
