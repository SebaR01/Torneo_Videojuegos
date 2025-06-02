package com.torneo.api.dto;

import lombok.*;

import java.util.Set;

/**
 * Este DTO se usa para mostrar los datos de un jugador.
 * Incluye información personal y los IDs de los equipos a los que pertenece.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerResponseDTO {
    private Integer id;
    private String nickname;
    private String name;
    private String lastName;
    private String email;
    private Integer age;
    private Set<Integer> teamsIds;
}
