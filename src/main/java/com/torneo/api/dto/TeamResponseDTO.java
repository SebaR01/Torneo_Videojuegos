package com.torneo.api.dto;

import lombok.*;

import java.util.Set;

/**
 * Este DTO se usa para enviar los datos de un equipo al cliente.
 * Incluye el nombre del equipo, los IDs de sus jugadores y el ID del torneo al que pertenece.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamResponseDTO {
    private Long id; // Cambiado de Integer a Long
    private String name;
    private Set<Long> playersIds; // Cambiado de Set<Integer> a Set<Long>
    private Long tournamentId;
}
