package com.torneo.api.dto;

import com.torneo.api.enums.MatchStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO para crear o actualizar un partido.
 * Utiliza IDs en lugar de entidades para simplificar el intercambio de datos.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchRequestDTO {

    @NotNull
    private Long tournamentId;

    @NotNull
    private LocalDateTime matchDate;

    @NotNull
    private Long firstTeamId;

    @NotNull
    private Long secondTeamId;

    @Min(0)
    private Integer firstTeamScore;

    @Min(0)
    private Integer secondTeamScore;

    @NotNull
    private MatchStatus status;
}
