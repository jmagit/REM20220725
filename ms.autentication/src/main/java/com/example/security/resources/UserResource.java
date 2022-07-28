package com.example.security.resources;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.dtos.AuthToken;
import com.example.security.dtos.BasicCredential;
import com.example.security.repositories.UsuarioRepositoy;
import com.netflix.eureka.registry.rule.AlwaysMatchInstanceStatusRule;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
//	@CrossOrigin(origins = "http://localhost:4200", allowCredentials="true", methods={RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS })
//	@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials="false")
public class UserResource {
	@Value("${jwt.secret}")
	private String SECRET;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UsuarioRepositoy dao;

	// { "username": "adm@example.com", "password": "P@$$w0rd" }

	@PostMapping(path = "/login", consumes = "application/json")
	public AuthToken loginJSON(@Valid @RequestBody BasicCredential credential) {
		var item = dao.findById(credential.getUsername());
		System.out.println(credential.getUsername());
		if (item.isEmpty() || !passwordEncoder.matches(credential.getPassword(), item.get().getPassword()))
			return new AuthToken();
		var usr = item.get();
		String token = Jwts.builder()
				.setId("MicroserviciosJWT")
				.setSubject(usr.getIdUsuario())
				.claim("authorities", usr.getRoles())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
				.compact();
		return new AuthToken(true, "Bearer " + token, usr.getNombre());
	}

	/*
	 * /register (anonimo) /changepassword /profile (Authorization) (get, put) menos
	 * la contraseña /users (Admin) (get, post, put, delete) + roles menos la
	 * contraseña
	 *
	 */
}
