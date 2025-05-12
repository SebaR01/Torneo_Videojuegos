package com.torneo.api.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/*
 * Reseña:
 * Esta clase representa la inscripción de un equipo en un torneo.
 * Contiene información sobre el equipo, el torneo, la fecha de inscripción
 * y el costo asociado. Es una entidad intermedia para modelar la relación
 * entre equipos y torneos con información adicional.
 */

@Entity
@Table(name = "inscriptions")
@Data
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    private LocalDate fechaInscripcion;

    @Column(precision = 10, scale = 2) // Ejemplo: 99999999.99
    private BigDecimal costoInscripcion;
}
