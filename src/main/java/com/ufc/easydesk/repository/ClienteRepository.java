package com.ufc.easydesk.repository;

import com.ufc.easydesk.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
