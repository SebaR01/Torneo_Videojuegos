package com.torneo.api.DTO;

import ENUMS.GamesCategory;
import ENUMS.GamesState;
import com.torneo.api.Models.User;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class TournamentCreateDTO
{
    private Long id;

    @NotNull(message = "name atribute is not a null camp. You must write something.")
    @Size(max = 100, message = "Size tournament name cannot have got more than one hundred chars.")
    private String name;

    @NotNull(message = "game atribute is not a null camp.")
    @Size(max = 50, message = "game name must have got 50 chars or less.")
    private String game;

    private GamesCategory gamesCategory;

    private Date dateInit;
    @Future(message = "La fecha de fin debe estar en el futuro")
    private Date dateFinish;

    private GamesState gamesState;

    private User organizerId;
}
