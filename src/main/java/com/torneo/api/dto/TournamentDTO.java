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
 * Este DTO se usa para enviar o recibir datos de un torneo.
 * No representa la entidad real, sino una versión simplificada para intercambiar con el frontend.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TournamentDTO {

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

    @NotNull
    private User organizerId;

    @NotNull
    private Date startDate;

    @Future(message = "La fecha de fin debe estar en el futuro")
    private Date endDate;
}
