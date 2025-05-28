package com.torneo.api.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(
        description = "Representa una entidad de resultado en el sistema, que contiene detalles sobre el resultado de un torneo, incluyendo los puntajes del equipo ganador y perdedor, el torneo asociado y los equipos involucrados."
)
@Entity //Anotación, proporcionada por la especificcación de Spring JPA, que establece que la presente clase es una entidad; por lo tanto, esa una tabla en la base de datos. Por lo tanto, los atributos de Result son campos en la base de datos.
@Table(name = "results", schema = "public") // Anotación de JPA que define el nombre que la tabla tendrá en la base de datos. Sin esta anotación, el nombre de la tabla será el mismo que el de la clase.
@Data //Anotación, proporcionada por la especificidad de Spring Lombok, lo que hace es generar, de forma automática, los métodos generales y básicos como getters, setters, equals, ToString y compareTO.
public class Result {

    @Schema(
            description = "Identificador único del resultado, generado automáticamente y autoincremental.",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    @Id //Anotación, proporcionada por JPA, que marca al atributo por debajo como el campo ID de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(
            description = "Puntaje del equipo ganador. No puede ser nulo y debe ser mayor o igual a 0.",
            example = "10",
            required = true
    )
    @NotNull(message = "You must add a score. Must.")
    @Min(value = 0, message = "min value is 0.")
    private Long scoreWinnerTeam;

    @Schema(
            description = "Puntaje del equipo perdedor. No puede ser nulo y debe ser mayor o igual a 0.",
            example = "5",
            required = true
    )
    @NotNull
    @Min(value = 0, message = "min value is 0.")
    private Long scoreLoserTeam;

    @Schema(
            description = "Torneo asociado al resultado. Referencia a la entidad Tournament.",
            example = "1",
            required = true
    )
    @OneToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournamentId;

    @Schema(
            description = "Equipo perdedor del torneo. Referencia a la entidad Team.",
            example = "2"
    )
    @OneToOne
    @JoinColumn(name = "loser_team_id")
    private Team loserTeamId;

    @Schema(
            description = "Equipo ganador del torneo. Referencia a la entidad Team.",
            example = "3"
    )
    @OneToOne
    @JoinColumn(name = "winner_team_id")
    private Team winnerTeamId; // Corregido el typo de "winerTeamId" a "winnerTeamId"
}
