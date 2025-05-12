package com.torneo.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Reseña:
 * Esta clase configura la documentación Swagger (OpenAPI) de forma personalizada.
 * Permite que al acceder a /swagger-ui.html se muestre el título, versión y descripción de la API.
 * Es útil para que cualquier persona que use la API entienda qué hace cada endpoint.
 */

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("API de Gestión de Torneos de Videojuegos")
                        .version("1.0")
                        .description("Documentación interactiva del Trabajo Práctico Final – Programación III")
        );
    }
}
