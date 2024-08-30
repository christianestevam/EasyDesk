package com.ufc.easydesk.repository;

import com.ufc.easydesk.model.Comanda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComandaRepository extends JpaRepository<Comanda, Long> {
}
