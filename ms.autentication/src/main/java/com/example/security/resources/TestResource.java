package com.example.security.resources;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class TestResource {
	@Autowired
	PasswordEncoder passwordEncoder;

	@GetMapping("/seguro")
	@SecurityRequirement(name = "bearerAuth")
	public String get(@Parameter(hidden = true) @RequestHeader String authorization, Principal principal) {
		return "El usuario está autenticado: " + principal.getName() + "\nAuthorization: " + authorization;
	}
	
	@GetMapping("/pass")
	public String getPass(String pass) {
		return passwordEncoder.encode(pass);
	}
	@GetMapping("/val")
	public String getVal(String pass, String cmp) {
		return passwordEncoder.matches(pass, cmp) ? "OK":"KO";
	}
	
	@GetMapping("/admin")
	@SecurityRequirement(name = "bearerAuth")
	public String getAdmin() {
		return "El usuario es administrador";
	}
	
	@Value("${jwt.secret}")
	private String SECRET;
	
	@GetMapping("/secreto")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@SecurityRequirement(name = "bearerAuth")
	public String getSecreto() {
		return SECRET;
	}
}
