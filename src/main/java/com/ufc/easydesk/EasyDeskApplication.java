package com.ufc.easydesk;

import com.ufc.easydesk.domain.model.Role;
import com.ufc.easydesk.domain.enums.RoleName;
import com.ufc.easydesk.domain.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class EasyDeskApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(EasyDeskApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (roleRepository.count() == 0) {
			Arrays.stream(RoleName.values()).forEach(roleName -> {
				Role role = new Role();
				role.setNome(roleName);
				roleRepository.save(role);
			});
			System.out.println("Roles iniciais foram inseridas no banco de dados.");
		} else {
			System.out.println("Roles já existem no banco de dados.");
		}
	}
}
