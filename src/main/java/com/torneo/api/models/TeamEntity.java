package com.torneo.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

/**
 * Esta clase representa un equipo de jugadores.
 * Cada equipo puede tener múltiples jugadores y participar en un torneo.
 */

@Entity
@Table(name = "equipos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(max = 50)
    @Column(name = "nombre")
    private String name;

    @ManyToMany
    @JoinTable(
            name = "equipo_jugador",
            joinColumns = @JoinColumn(name = "equipo_id"),
            inverseJoinColumns = @JoinColumn(name = "jugador_id")
    )
    private Set<PlayerEntity> players;

    @Column(name = "torneo_id")
    private Long tournamentId;
}
