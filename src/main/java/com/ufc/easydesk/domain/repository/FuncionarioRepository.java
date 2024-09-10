package com.ufc.easydesk.domain.repository;

import com.ufc.easydesk.domain.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}
