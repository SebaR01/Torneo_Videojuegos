package com.torneo.api.models;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa a un jugador dentro de un equipo.
 *
 * ✔ Cada jugador tiene un nombre, un apodo y pertenece a un equipo.
 */

@Entity
@Table(name = "players")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private TeamEntity team;
}
