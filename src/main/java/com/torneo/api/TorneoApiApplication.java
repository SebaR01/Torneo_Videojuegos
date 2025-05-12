package com.torneo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Reseña:
 * Clase principal de arranque de la aplicación Spring Boot.
 * Contiene el metodo main que inicia el contexto de Spring y ejecuta la API.
 */

@SpringBootApplication
public class TorneoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TorneoApiApplication.class, args);
	}
}
