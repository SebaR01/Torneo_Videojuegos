package com.torneo.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

/**
 * ✅ Representa un equipo dentro del sistema.
 *
 * 🔹 Cada equipo tiene un nombre, país y un capitán asignado.
 * 🔹 Puede tener varios jugadores (relación @ManyToMany con PlayerEntity).
 * 🔹 Se relaciona con un torneo por su ID (Long).
 */
@Entity
@Table(name = "teams")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ✅ ID Long para mantener coherencia

    @NotBlank(message = "El nombre del equipo no puede estar vacío.")
    private String name;

    @NotBlank(message = "El país no puede estar vacío.")
    private String country;

    // ✅ Capitán del equipo: relación con un jugador
    @ManyToOne(optional = false)
    @JoinColumn(name = "captain_id")
    private PlayerEntity captain;

    // ✅ Relación muchos a muchos con jugadores
    @ManyToMany
    @JoinTable(
            name = "team_player",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private Set<PlayerEntity> players;

    // ✅ Relación con torneo (por ID, no entidad)
    @Column(name = "tournament_id", nullable = false)
    private Long tournamentId;
}
