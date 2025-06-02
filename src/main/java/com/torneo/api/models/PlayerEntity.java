package com.torneo.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

/**
 * Esta clase representa a un jugador dentro del sistema.
 * Puede formar parte de uno o varios equipos.
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
    private Integer id;

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
