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
public class EquipoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(max = 50)
    private String nombre;

    @ManyToMany
    @JoinTable(
            name = "equipo_jugador",
            joinColumns = @JoinColumn(name = "equipo_id"),
            inverseJoinColumns = @JoinColumn(name = "jugador_id")
    )
    private Set<JugadorEntity> jugadores;

    @Column(name = "torneo_id")
    private Long torneoId;
}
