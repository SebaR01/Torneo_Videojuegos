/**
 * Entidad que representa un equipo participante en torneos de videojuegos.
 *
 * ✔ Tiene nombre único.
 * ✔ Se relaciona con un torneo específico (`ManyToOne`).
 * ✔ Puede tener múltiples jugadores (`OneToMany` con `PlayerEntity`).
 * ✔ Puede estar inscrito en múltiples torneos mediante `Inscription`.
 */

package com.torneo.api.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    // Relación con torneo (opcional)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    // Jugadores del equipo
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayerEntity> players;

    // Inscripciones a torneos
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inscription> inscriptions;

    @Column(nullable = false, unique = true)
    private String email;

}
