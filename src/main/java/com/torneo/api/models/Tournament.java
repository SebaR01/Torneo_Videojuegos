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
@Entity //Gracias a esta anotación @Entity, que me la proporciona la especificación de Spring JPA, esta clase será considerada una entidad; por ende, es una tabla en la base de datos. Los atributos de la clase, por su parte, son campos de dicha tabla.
@Table(name = "torneos") //Esta anotación también me la proporciona la especificación de Spring JPA. Lo que hace es asignar el nombre de la tabla de la base de datos cuyos campos serán los atributos de la presente clase. Si no utilizo esta anotación, el nombre de la tabla por defecto es el mismo que el de la clase.
@Data //Anotacion, proporcionada por la especificación de Spring Lombok, que me génera automáticamente varios métodos básicos como los getter, setter, equals, compareTo, ToString..
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tournament {

    @Schema(
            description = "Identificador único del torneo, generado automáticamente. Autoincremental",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    @Id //Anotación, que también me la proporciona la especificación JPA, que identifica al atributo abajo de ella como el ID de la tabla. Es obligatoria la existencia de esta anotación.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(
            description = "Nombre del torneo. No puede ser nulo y no debe exceder los 100 caracteres.",
            example = "Campeonato de fifita",
            required = true
    )
    @NotNull(message = "El nombre no puede ser nulo.")
    @Size(max = 100, message = "El nombre del torneo no puede tener más de 100 caracteres.")
    private String name;

    @Schema(
            description = "Fecha de inicio del torneo. No puede ser nula.",
            example = "2025-06-01",
            required = true
    )
    @NotNull
    private Date startDate;

    @Schema(
            description = "Fecha de fin del torneo. Debe ser una fecha futura y no puede ser nula.",
            example = "2025-06-07",
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
            description = "Categoría del juego (por ejemplo, Sports, Shooter, Estrategia).",
            example = "MOBA",
            allowableValues = {"SPORTS", "SHOOTER", "ESTRATEGIA", "LUCHA", "SURVIVAL HORROR"}
    )
    @Enumerated(EnumType.STRING)
    private GamesCategory category;

    @Schema(
            description = "Estado actual del torneo (por ejemplo, ACTIVE, FINISHED, NEXT).",
            example = "PLANIFICADO",
            allowableValues = {"ACTIVE", "FINISHED", "NEXT"}
    )
    @Enumerated(EnumType.STRING)
    private GamesState state;

    @Schema(
            description = "ID del organizador del torneo. Referencia a la entidad del organizador.",
            example = "1001"
    )
    @ManyToOne
    @JoinColumn(name = "organizerId") //foraign key
    private User organizerID;
}