package com.torneo.api.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * ✅ Representa la inscripción de un equipo a un torneo.
 *
 * 🔹 Se vincula con un equipo y un torneo (ambos obligatorios).
 * 🔹 Registra la fecha de inscripción.
 */
@Entity
@Table(name = "inscriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ Relación con equipo
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private TeamEntity team;

    // ✅ Relación con torneo
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    // ✅ Fecha en la que se registró la inscripción
    @Column(name = "fecha_inscripcion", nullable = false)
    private LocalDateTime fechaInscripcion;
}
