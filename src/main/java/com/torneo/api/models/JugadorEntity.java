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
@Table(name = "jugadores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JugadorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(max = 50)
    private String nickname;

    @NotNull
    private String nombre;

    @NotNull
    private String apellido;

    @Email
    private String email;

    @Min(13)
    private Integer edad;

    @ManyToMany(mappedBy = "jugadores")
    private Set<EquipoEntity> equipos;
}
