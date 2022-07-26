package com.example.domains.entities.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.example.domains.entities.Actor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Schema(name="Actor (corto)", description="Datos mínimos del actor")
@Data @AllArgsConstructor @NoArgsConstructor
public class ActorShort {
	@NotNull
	private int actorId;
	/**
	 * Nombre del actor
	 */
	@NotBlank
	@Size(min = 2, max = 45)
	@Schema(description = "Nombre del actor")
	private String firstName;
	/**
	 * Apellidos del actor
	 */
	@Size(min = 2, max = 45)
	@Schema(description = "Apellidos del actor")
	private String lastName;

	public static Actor from(ActorShort source) {
		return new Actor(
				source.getActorId(),
				source.getFirstName(),
				source.getLastName()
				);
	}
	public static ActorShort from(Actor source) {
		return new ActorShort(
				source.getActorId(),
				source.getFirstName(),
				source.getLastName()
				);
	}
}
