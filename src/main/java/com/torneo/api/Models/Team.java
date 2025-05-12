package com.torneo.api.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/*
 * Reseña:
 * Esta clase representa un equipo participante en torneos.
 * Cada equipo tiene un nombre y una lista de jugadores asociados mediante una relación ManyToMany.
 */

@Entity
@Table(name="teams", schema="public")
@Data
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "team_players",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private Set<Player> players = new HashSet<>();
}

