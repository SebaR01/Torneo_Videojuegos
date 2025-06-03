package com.torneo.api.dto;

import com.torneo.api.models.TeamEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchResponseDTO {
    private Integer id;
    private Long tournamentId;
    private TeamEntity firstTeam;
    private TeamEntity secondTeam;
    private LocalDateTime matchDate;
    private Integer firstTeamScore;
    private Integer secondTeamScore;
}
