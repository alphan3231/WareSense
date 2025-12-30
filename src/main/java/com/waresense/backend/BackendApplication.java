package com.waresense.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@org.springframework.context.annotation.Bean
	public org.springframework.boot.CommandLineRunner commandLineRunner(org.springframework.core.env.Environment env, com.waresense.backend.repository.RoleRepository roleRepository) {
		return args -> {
			// Seed Roles
			java.util.List<String> roles = java.util.Arrays.asList("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_COURIER");
			for (String roleName : roles) {
				if (roleRepository.findByName(roleName).isEmpty()) {
					roleRepository.save(com.waresense.backend.entity.Role.builder().name(roleName).build());
				}
			}

			String port = env.getProperty("server.port", "8080");
			System.out.println("\n\n=============================================================");
			System.out.println("🚀 WareSense Backend Started Successfully!");
			System.out.println("🌍 API URL: http://localhost:" + port);
			System.out.println("📚 Swagger UI: http://localhost:" + port + "/swagger-ui/index.html");
			System.out.println("=============================================================\n\n");
		};
	}
}
