package com.torneo.api.models;

import com.torneo.api.enums.MatchStatus;
import jakarta.persistence.*;
import lombok.*;

/**
 * Representa un partido entre dos equipos dentro de un torneo.
 * Se usa para planificar y registrar resultados en un torneo tipo "todos contra todos".
 */
@Entity
@Table(name = "matches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Tournament tournament;

    @ManyToOne(optional = false)
    @JoinColumn(name = "team_a_id")
    private TeamEntity teamA;

    @ManyToOne(optional = false)
    @JoinColumn(name = "team_b_id")
    private TeamEntity teamB;

    private String resultado; // Ejemplo: "3-1", "Empate", o null si no se jugó

    @Enumerated(EnumType.STRING)
    private MatchStatus status;
}
