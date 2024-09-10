package com.ufc.easydesk.domain.repository;

import com.ufc.easydesk.domain.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MesaRepository extends JpaRepository<Mesa, Long> {
    List<Mesa> findByRestauranteId(Long restauranteId);
}
