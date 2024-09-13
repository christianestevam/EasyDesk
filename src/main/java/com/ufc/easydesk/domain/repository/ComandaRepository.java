package com.ufc.easydesk.domain.repository;

import com.ufc.easydesk.domain.model.Comanda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComandaRepository extends JpaRepository<Comanda, Long> {

    List<Comanda> findByAtivaTrue();
}