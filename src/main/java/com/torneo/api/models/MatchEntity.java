package com.torneo.api.models;

import com.torneo.api.enums.MatchStatus;
import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Esta clase representa un partido (match) dentro del sistema.
 * Está asociado a un torneo y a dos equipos (local y visitante).
 * Guarda información de fecha y resultados.
 */
@Schema(description = "Entidad que representa un partido dentro de un torneo.")
@Entity
@Table(name = "matches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private LocalDateTime matchDate;
    @NotNull
    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    // Relación con torneo
    @NotNull
    @JoinColumn(name = "torneo_id")
    private Long tournamentId;

    // Relación con equipos participantes
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "firstTeamId")
    private TeamEntity firstTeam;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secondTeamId")
    private TeamEntity secondTeam;

    // Resultados, pueden ser nulos si el partido no se jugó todavía
    @JoinColumn(name = "firstTeamScore")
    @Min(0)
    private Integer firstTeamScore;

    @JoinColumn(name = "secondTeamScore")
    @Min(0)
    private Integer secondTeamScore;
}