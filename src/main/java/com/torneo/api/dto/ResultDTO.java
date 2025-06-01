package com.torneo.api.dto;

import com.torneo.api.models.Tournament;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResultDTO
{
    private Long id;

    @NotNull(message = "You must add a score. Must.")
    @Min(value = 0, message = "min value is 0.")
    private Long scoreWinnerTeam;

    @NotNull
    @Min(value = 0, message = "min value is 0.")
    private Long scoreLoserTeam;

    private Tournament tournamentId;

    private Team loserTeamId;

    private Team winerTeamId;
}
