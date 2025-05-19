package com.torneo.api.models;

import com.torneo.api.enums.GamesCategory;
import com.torneo.api.enums.GamesState;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

/**
 * Esta clase representa la entidad Torneo que se guarda en la base de datos.
 * Incluye información como nombre, juego, fechas, estado, categoría y organizador.
 * Está mapeada como una tabla llamada 'torneos'.
 */
@Entity
@Table(name = "torneos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El nombre no puede ser nulo.")
    @Size(max = 100, message = "El nombre del torneo no puede tener más de 100 caracteres.")
    private String name;

    @NotNull
    private Date startDate;

    @NotNull
    @Future(message = "La fecha de fin debe estar en el futuro")
    private Date endDate;

    @NotNull(message = "El juego no puede ser nulo.")
    @Size(max = 50, message = "El nombre del juego no puede tener más de 50 caracteres.")
    private String game;

    @Enumerated(EnumType.STRING)
    private GamesCategory category;

    @Enumerated(EnumType.STRING)
    private GamesState state;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private Long organizerId;
}
