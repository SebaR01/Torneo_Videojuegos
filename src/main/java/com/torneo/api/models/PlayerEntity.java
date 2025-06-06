package com.torneo.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

/**
 * ✅ Representa a un jugador del sistema.
 *
 * 🔹 Cada jugador tiene un nickname, nombre, apellido, email y edad.
 * 🔹 Puede formar parte de uno o varios equipos (relación @ManyToMany).
 * 🔹 Esta clase usa Long como tipo de ID para mantener coherencia en todo el sistema.
 */
@Entity
@Table(name = "players")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ✅ ID unificado como Long

    @NotNull
    @Size(max = 50)
    private String nickname;

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @Email
    private String email;

    @Min(13)
    private Integer age;

    @ManyToMany(mappedBy = "players")
    private Set<TeamEntity> teams;
}
