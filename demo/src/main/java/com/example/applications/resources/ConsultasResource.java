package com.example.applications.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.applications.dtos.ActorRecibido;
import com.example.applications.dtos.Pelicula;
import com.example.applications.proxies.CatalogoProxy;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/v1/consultas")
public class ConsultasResource {
	@Autowired
	RestTemplate srvRest;
	
	@GetMapping
	@SecurityRequirement(name = "bearerAuth")
	public String getInicio() {
		return srvRest.getForObject("lb://catalogo-service/", String.class);
//		return srvRest.getForObject("http://localhost:8010/", String.class);
	}
	
	
	@GetMapping(path = "/uno/{id}")
	@SecurityRequirement(name = "bearerAuth")
	public ActorRecibido dameUno(@PathVariable int id) {
		return srvRest.getForObject("lb://catalogo-service/v1/actores/{id}", ActorRecibido.class, id);
//		return srvRest.getForObject("http://localhost:8010/v1/actores/{id}", ActorRecibido.class, id);
	}

	@GetMapping(path = "/muchos")
	@SecurityRequirement(name = "bearerAuth")
	public List<Pelicula> dameMuchos() {
		ResponseEntity<List<Pelicula>> response = srvRest.exchange(
					"http://localhost:8010/v1/peliculas?mode=short", 
					HttpMethod.GET,
					HttpEntity.EMPTY, 
					new ParameterizedTypeReference<List<Pelicula>>() {
				});

		return response.getBody();
	}
	
	@Autowired
	CatalogoProxy proxy;

	@GetMapping(path = "/proxy")
	@SecurityRequirement(name = "bearerAuth")
	public String getInicioProxy() {
		return proxy.damaInicio();
	}
	
	
	@GetMapping(path = "/proxy/uno/{id}")
	@SecurityRequirement(name = "bearerAuth")
	public ActorRecibido dameUnoProxy(@PathVariable int id, @RequestHeader String authorization) {
		return proxy.dameUnActor(id, authorization);
	}

	@GetMapping(path = "/proxy/muchos")
	@SecurityRequirement(name = "bearerAuth")
	public List<Pelicula> dameMuchosProxy() {
		return proxy.damePeliculas();
	}

	@Value("${particular.para.demos}")
	String miConfigString;
	
	@GetMapping(path = "/config")
	@SecurityRequirement(name = "bearerAuth")
	public String dameConfig() {
		return miConfigString;
	}
}
