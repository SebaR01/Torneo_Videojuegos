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
public class JugadorResponseDTO {
    private Integer id;
    private String nickname;
    private String nombre;
    private String apellido;
    private String email;
    private Integer edad;
    private Set<Integer> equiposIds;
}
