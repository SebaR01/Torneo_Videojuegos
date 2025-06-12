package com.torneo.api.dto;

import lombok.*;

/**
 * DTO de salida para mostrar los datos de un jugador.
 * Incluye nombre, apodo e información básica del equipo.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerResponseDTO {
    private Long id;
    private String name;
    private String nickname;
    private String teamName;
}
