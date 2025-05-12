package com.torneo.api.Models;

import com.torneo.api.enums.GamesCategory;
import com.torneo.api.enums.GamesState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "torneos", schema = "public")
@Data
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "name atribute is not a null camp. You must write something.")
    @Size(max = 100, message = "Size tournament name cannot have got more than one hundred chars.")
    private String name;

    @NotNull(message = "game atribute is not a null camp.")
    @Size(max = 50, message = "game name must have got 50 chars or less.")
    private String game;

    @Enumerated(EnumType.STRING)
    private GamesCategory gamesCategory;

    @Enumerated(EnumType.STRING)
    private GamesState gamesState;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizerId;

    @Column(precision = 10, scale = 2) // nuevo campo para el costo de inscripción
    private BigDecimal cost;
}
