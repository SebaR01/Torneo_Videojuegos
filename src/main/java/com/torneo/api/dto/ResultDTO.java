package com.torneo.api.dto;

import com.torneo.api.Models.EquipoEntity;
import com.torneo.api.Models.Tournament;
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

    private EquipoEntity loserTeamId;

    private EquipoEntity winerTeamId;
}
