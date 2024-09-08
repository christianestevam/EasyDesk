package com.ufc.easydesk.repository;

import com.ufc.easydesk.model.Role;
import com.ufc.easydesk.model.enums.RoleName; // Supondo que a enum RoleName esteja no pacote enums
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByNome(RoleName roleName); // Use 'nome' ao invés de 'name' se esse for o campo correto
}
