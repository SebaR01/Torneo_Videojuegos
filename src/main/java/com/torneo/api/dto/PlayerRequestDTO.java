package com.torneo.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO para registrar o actualizar un jugador.
 * Recibe nombre, apodo y el ID del equipo al que pertenece.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerRequestDTO {

    @NotNull(message = "El nombre del jugador es obligatorio")
    private String name;

    @NotNull(message = "El apodo del jugador es obligatorio")
    private String nickname;

    @NotNull(message = "El ID del equipo es obligatorio")
    private Long teamId;
}
