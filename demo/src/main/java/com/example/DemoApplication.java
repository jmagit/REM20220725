package com.example;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.dtos.ActorShort;
import com.example.domains.entities.dtos.ActorShort2;
import com.example.domains.entities.dtos.NamesOnly;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients("com.example.applications.proxies")
@OpenAPIDefinition(
        info = @Info(
                title = "Microservicio: Demos",
                version = "1.0",
                description = "**Demos** de Microservicios.",
                license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(name = "Javier Martín", url = "https://github.com/jmagit", email = "support@example.com")
        ),
        externalDocs = @ExternalDocumentation(description = "Documentación del proyecto", url = "https://github.com/jmagit/REM20220725")
)
public class DemoApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.out.println("Aplicación arrancada");
//		consultas();
	}

	@Autowired
	ActorRepository dao;
	@Autowired
	ActorService srv;
	
	@Transactional
	private void consultas() {
//		var ele = dao.findById(1);
//		if(ele.isPresent()) {
//			var actor = ele.get();
//			actor.setFirstName(actor.getFirstName().toUpperCase());
//			dao.save(actor);
//		}
//		var actor = new Actor(0, "Pepito", "Grillo");
//		dao.save(actor);
//		dao.deleteById(219);
//		dao.findAll().forEach(item -> System.out.println(item));
//		dao.findByActorIdBetween(5,10).forEach(item -> System.out.println(item));
//		dao.findTop5ByFirstNameStartingWithOrderByFirstName("p").forEach(item -> System.out.println(item));
//		System.out.println(dao.countByActorIdLessThan(10));
//		dao.findAll(Sort.by("firstName","lastName")).forEach(item -> System.out.println(item));
//		dao.findByActorIdBetween( 5, 10, Sort.by("firstName","lastName")).forEach(item -> System.out.println(item));
//		dao.findAll(PageRequest.of(5, 10, Sort.by("actorId"))).getContent().forEach(item -> System.out.println(item));
//		dao.consultaSQL(200).forEach(item -> System.out.println(item));
//		var ele = dao.findById(1);
//		if(ele.isPresent()) {
//			var actor = ele.get();
//			actor.getFilmActors().forEach(item -> System.out.println(item.getFilm().getTitle()));
//		}
//		var actor = new Actor(0, "PEPITO", "12345678Z");
//		if(actor.isInvalid())
//			System.out.println(actor.getErrorsMessage());
//		else
//			System.out.println(ActorShort.from(actor));
//		//dao.save(actor);
////		var a = new ActorShort();
//		System.out.println(ActorShort.from(new ActorShort(1, "Desde", "DTO")));
//		dao.findAll().stream().map(item -> ActorShort.from(item)).toList().forEach(System.out::println);
//		dao.findTop5ByFirstNameStartingWithOrderByFirstName("p").forEach(item -> System.out.println(item.getId() + " " + item.getNombre()));
//		dao.findByActorIdNotNull(NamesOnly.class).forEach(item -> System.out.println(item.getId() + " " + item.getNombre()));
//		dao.findByActorIdIsNotNull(ActorShort.class).forEach(item -> System.out.println(item));
//		
//		try {
//			srv.add(new Actor(0, "PEPITO", "12345678Z"));
//		} catch (DuplicateKeyException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvalidDataException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		srv.getAll().forEach(System.out::println);
	}
	
//	  @Bean
//	  public OpenAPI springShopOpenAPI() {
//	      return new OpenAPI()
//	              .info(new Info().title("Microservicio: Demos")
//	            		  .description("**Demos** de Microservicios.")
//	            		  .version("1.0")
//	            		  .license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html"))
//	            		  .contact(new Contact().name("Javier Martín").url("https://github.com/jmagit").email("support@example.com"))
//	              ).externalDocs(
//	            		  new ExternalDocumentation()
//	            		  	.description("Documentación del proyecto")
//	            		  	.url("https://github.com/jmagit/REM20220725")
//	            		  	);
//	  }


	@Bean 
	@LoadBalanced
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

}
