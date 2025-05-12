package com.torneo.api.DTO;

import com.torneo.api.enums.GamesCategory;
import com.torneo.api.enums.GamesState;
import com.torneo.api.Models.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TournamentDTO
{
    private Long id;

    @NotNull(message = "name atribute is not a null camp. You must write something.")
    @Size(max = 100, message = "Size tournament name cannot have got more than one hundred chars.")
    private String name;

    @NotNull(message = "game atribute is not a null camp.")
    @Size(max = 50, message = "game name must have got 50 chars or less.")
    private String game;

    private GamesCategory gamesCategory;

    private GamesState gamesState;

    private User organizerId;
}
