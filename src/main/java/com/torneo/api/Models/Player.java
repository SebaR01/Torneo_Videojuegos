package com.torneo.api.Models;

import jakarta.persistence.*;
import lombok.Data;

/*
 * Reseña:
 * Esta clase representa a un jugador dentro del sistema.
 * Se usa para mapear la entidad Player en la base de datos con JPA.
 */

@Entity
@Table(name = "players")
@Data
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private String nationality;
}
