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
public class EquipoResponseDTO {
    private Integer id;
    private String nombre;
    private Set<Integer> jugadoresIds;
    private Long torneoId;
}
