package com.torneo.api.dto;

import com.torneo.api.enums.MatchStatus;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO que representa la información devuelta al consultar un partido.
 * Contiene los IDs de los equipos y el estado del partido.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchResponseDTO {
    private Long id;
    private Long tournamentId;
    private Long firstTeamId;
    private Long secondTeamId;
    private LocalDateTime matchDate;
    private Integer firstTeamScore;
    private Integer secondTeamScore;
    private MatchStatus status;
}
