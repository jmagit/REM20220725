package com.example.domains.entities.dtos;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.example.domains.entities.Film;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Value;

@Schema(name = "Pelicula (Detalles)", description = "Version completa de las peliculas")
@Value
public class FilmDetailsDTO {
	@Schema(description = "Identificador de la pelicula", required = true, accessMode = AccessMode.READ_ONLY)
	private int filmId;
	@Schema(description = "Descripcion de la pelicula")
	private String description;
	@Schema(description = "Duración de la pelicula", required = true)
	private int length;
	private String rating;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
	private Short releaseYear;
	private byte rentalDuration;
	private BigDecimal rentalRate;
	private BigDecimal replacementCost;
	@Schema(description = "Titulo de la pelicula", required = true, allowableValues = "Un maximo de 255 caracteres")
	private String title;
	private String language;
	private String languageVO;
	private List<String> actors;
	private List<String> categories;
	
	public static FilmDetailsDTO from(Film source) {
		return new FilmDetailsDTO(
				source.getFilmId(), 
				source.getDescription(),
				source.getLength(),
				source.getRating(),
				source.getReleaseYear(),
				source.getRentalDuration(),
				source.getRentalRate(),
				source.getReplacementCost(),
				source.getTitle(),
				source.getLanguage() == null ? null : source.getLanguage().getName(),
				source.getLanguageVO() == null ? null : source.getLanguageVO().getName(),
				source.getFilmActors().stream().map(item -> item.getActor().getFirstName() + " " + item.getActor().getLastName())
					.sorted()
					.collect(Collectors.toList()),
				source.getFilmCategories().stream().map(item -> item.getCategory().getName())
					.collect(Collectors.toList())
				);
	}
}
