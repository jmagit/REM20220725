package com.example.domains.contracts.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.example.domains.entities.Actor;
import com.example.domains.entities.dtos.NamesOnly;

@RepositoryRestResource(exported = false)
public interface ActorRepository extends JpaRepository<Actor, Integer> {
	List<NamesOnly> findTop5ByFirstNameStartingWithOrderByFirstName(String prefijo);
	List<Actor> findByActorIdBetween(int vInicial, int vFinal);
	@RestResource(exported = false)
	List<Actor> findByActorIdBetween(int vInicial, int vFinal, Sort orden);
	
	long countByActorIdLessThan(int valor);
	
	@Query("SELECT a FROM Actor a where a.actorId > ?1")
	List<Actor> consulta(int id);
	@Query(value =  "select * from actor where actor_id > ?1", nativeQuery = true)
	List<Actor> consultaSQL(int id);
	
	@RestResource(exported = false)
	<T> List<T> findByActorIdIsNotNull(Class<T> type);
	@RestResource(exported = false)
	<T> Iterable<T> findByActorIdIsNotNull(Sort sort, Class<T> type);
	@RestResource(exported = false)
	<T> Page<T> findByActorIdIsNotNull(Pageable pageable, Class<T> type);
}
