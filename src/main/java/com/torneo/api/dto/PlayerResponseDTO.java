package com.torneo.api.dto;

import lombok.*;

import java.util.Set;

/**
 * Este DTO se usa para mostrar los datos de un jugador.
 * Incluye información personal y los IDs de los equipos a los que pertenece.
 *
 * ⚠️ Ahora todos los IDs son Long para mantener coherencia en todo el sistema.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerResponseDTO {
    private Long id; // CAMBIO A LONG
    private String nickname;
    private String name;
    private String lastName;
    private String email;
    private Integer age;
    private Set<Long> teamsIds; // CAMBIO A LONG
}
