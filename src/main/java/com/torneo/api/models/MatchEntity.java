package com.torneo.api.models;

import com.torneo.api.enums.MatchStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * ✅ Representa un partido dentro de un torneo.
 *
 * 🔹 Participan dos equipos (local y visitante).
 * 🔹 Guarda fecha, estado y resultado del partido.
 * 🔹 Se relaciona con un torneo por ID (tipo Long).
 */
@Entity
@Table(name = "matches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha del partido no puede ser nula.")
    private LocalDateTime matchDate;

    @NotNull(message = "El estado del partido no puede ser nulo.")
    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    // ✅ ID del torneo asociado (coherente con el tipo Long en Tournament)
    @NotNull
    @Column(name = "torneo_id", nullable = false)
    private Long tournamentId;

    // ✅ Primer equipo (local)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_team_id", nullable = false)
    private TeamEntity firstTeam;

    // ✅ Segundo equipo (visitante)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_team_id", nullable = false)
    private TeamEntity secondTeam;

    // ✅ Resultados (pueden ser nulos si no se jugó)
    @Min(0)
    @Column(name = "first_team_score")
    private Integer firstTeamScore;

    @Min(0)
    @Column(name = "second_team_score")
    private Integer secondTeamScore;
}
