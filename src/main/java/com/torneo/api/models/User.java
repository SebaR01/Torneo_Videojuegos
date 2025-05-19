package com.torneo.api.models;

import jakarta.persistence.*;
import lombok.*;
import com.torneo.api.enums.Role;

/**
 * Esta clase representa un usuario del sistema.
 * Puede ser un ADMIN, ORGANIZADOR o JUGADOR.
 * Cada usuario tiene un nombre de usuario, contraseña encriptada y un rol.
 * Se guarda en la base de datos en la tabla 'users'.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
