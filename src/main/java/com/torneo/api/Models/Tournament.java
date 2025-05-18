package com.torneo.api.Models;

import ENUMS.GamesCategory;
import ENUMS.GamesState;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "torneos", schema = "public")
@Data
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "name attribute is not a null camp. You must write something.")
    @Size(max = 100, message = "Size tournament name cannot have got more than one hundred chars.")
    private String name;

    private Date dateInit;
    @Future(message = "La fecha de fin debe estar en el futuro")
    private Date dateFinish;

    @NotNull(message = "game attribute is not a null camp.")
    @Size(max = 50, message = "game name must have got 50 chars or less.")
    private String game;

    @Enumerated(EnumType.STRING)
    private GamesCategory gamesCategory;

    @Enumerated(EnumType.STRING)
    private GamesState gamesState;

    @ManyToOne
    @JoinColumn(name = "organizer_id") // Cambia "id" por "organizer_id"
    private User organizerId;
}
