package com.torneo.api.dto;

import com.torneo.api.enums.MatchStatus;
import com.torneo.api.models.TeamEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class MatchRequestDTO {

    @NotNull
    @JoinColumn(name = "torneo_id")
    private Long tournamentId;

    @NotNull
    private LocalDateTime matchDate;

    @NotNull
    @JoinColumn(name = "firstTeamId")
    private TeamEntity firstTeam;

    @NotNull
    @JoinColumn(name = "secondTeamId")
    private TeamEntity secondTeam;

    @JoinColumn(name = "firstTeamScore")
    @Min(0)
    private Integer firstTeamScore;

    @JoinColumn(name = "secondTeamScore")
    @Min(0)
    private Integer secondTeamScore;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MatchStatus status;




}
