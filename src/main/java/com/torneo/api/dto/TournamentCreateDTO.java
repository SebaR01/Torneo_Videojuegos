package com.torneo.api.dto;

import com.torneo.api.enums.GamesCategory;
import com.torneo.api.enums.GamesState;
import com.torneo.api.models.User;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

/**
 * Este DTO se usa cuando se quiere registrar un nuevo torneo.
 * Contiene los campos que el usuario debe completar desde el frontend.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TournamentCreateDTO {

    private Long id;

    @NotNull(message = "El nombre no puede ser nulo.")
    @Size(max = 100, message = "El nombre del torneo no puede tener más de 100 caracteres.")
    private String name;

    @NotNull(message = "El juego no puede ser nulo.")
    @Size(max = 50, message = "El nombre del juego no puede tener más de 50 caracteres.")
    private String game;

    @NotNull
    private GamesCategory category;

    @NotNull
    private GamesState state;


    private User organizerId;

    @NotNull
    private Date startDate;

    @NotNull
    @Future(message = "La fecha de fin debe estar en el futuro")
    private Date endDate;
}
