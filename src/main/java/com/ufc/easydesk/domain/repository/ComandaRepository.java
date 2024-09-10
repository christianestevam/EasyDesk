package com.ufc.easydesk.domain.repository;

import com.ufc.easydesk.domain.model.Comanda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComandaRepository extends JpaRepository<Comanda, Long> {
}
