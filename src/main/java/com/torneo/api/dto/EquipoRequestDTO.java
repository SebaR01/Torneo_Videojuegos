package com.torneo.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

/**
 * Este DTO se usa para registrar o actualizar un equipo.
 * Recibe el nombre, la lista de IDs de jugadores y el torneo al que pertenece.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipoRequestDTO {

    @NotNull
    @Size(max = 50)
    private String nombre;

    @NotNull
    private Set<Integer> jugadoresIds;

    private Long torneoId;
}
