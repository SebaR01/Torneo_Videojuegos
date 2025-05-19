package com.torneo.api.models;

import com.torneo.api.enums.GamesCategory;
import com.torneo.api.enums.GamesState;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(
        description = "Representa una entidad de torneo, que contiene detalles como el nombre, fechas, juego, categoría, estado y organizador."
)
@Entity
@Table(name = "torneos") //Nombre de la tabla en la base de datos.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tournament {

    @Schema(
            description = "Identificador único del torneo, generado automáticamente.",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(
            description = "Nombre del torneo. No puede ser nulo y no debe exceder los 100 caracteres.",
            example = "Campeonato de eSports de Verano",
            required = true
    )
    @NotNull(message = "El nombre no puede ser nulo.")
    @Size(max = 100, message = "El nombre del torneo no puede tener más de 100 caracteres.")
    private String name;

    @Schema(
            description = "Fecha de inicio del torneo. No puede ser nula.",
            example = "2025-06-01T00:00:00Z",
            required = true
    )
    @NotNull
    private Date startDate;

    @Schema(
            description = "Fecha de fin del torneo. Debe ser una fecha futura y no puede ser nula.",
            example = "2025-06-07T23:59:59Z",
            required = true
    )
    @NotNull
    @Future(message = "La fecha de fin debe estar en el futuro")
    private Date endDate;

    @Schema(
            description = "Nombre del juego del torneo. No puede ser nulo y no debe exceder los 50 caracteres.",
            example = "League of Legends",
            required = true
    )
    @NotNull(message = "El juego no puede ser nulo.")
    @Size(max = 50, message = "El nombre del juego no puede tener más de 50 caracteres.")
    private String game;

    @Schema(
            description = "Categoría del juego (por ejemplo, FPS, MOBA, Estrategia).",
            example = "MOBA",
            allowableValues = {"FPS", "MOBA", "ESTRATEGIA", "LUCHA", "DEPORTES"}
    )
    @Enumerated(EnumType.STRING)
    private GamesCategory category;

    @Schema(
            description = "Estado actual del torneo (por ejemplo, PLANIFICADO, EN_CURSO, COMPLETADO).",
            example = "PLANIFICADO",
            allowableValues = {"PLANIFICADO", "EN_CURSO", "COMPLETADO", "CANCELADO"}
    )
    @Enumerated(EnumType.STRING)
    private GamesState state;

    @Schema(
            description = "ID del organizador del torneo. Referencia a la entidad del organizador.",
            example = "1001"
    )
    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private Long organizerId;
}