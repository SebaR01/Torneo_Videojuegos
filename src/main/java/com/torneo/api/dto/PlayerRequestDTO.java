package com.torneo.api.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

/**
 * Este DTO se usa para crear o actualizar un jugador.
 * Recibe la información básica y los equipos a los que se quiere asignar.
 *
 * ⚠️ Ahora los IDs de los equipos son del tipo Long para mantener consistencia.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerRequestDTO {

    @NotNull
    @Size(max = 50)
    private String nickname;

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @Email
    private String email;

    @Min(13)
    private Integer age;

    private Set<Long> teamsIds; // CAMBIO A LONG
}
