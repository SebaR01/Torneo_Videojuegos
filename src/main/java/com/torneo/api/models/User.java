package com.torneo.api.models;

import com.torneo.api.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Esta clase representa a un usuario registrado en el sistema de torneos.
 * Cada usuario tiene un `username` único, un `email`, una `password` cifrada,
 * y un `role` (ADMIN, ORGANIZADOR, JUGADOR).
 *
 * Implementa la interfaz `UserDetails` de Spring Security, lo que permite que
 * la clase sea utilizada directamente en el proceso de autenticación JWT.
 *
 * El campo `email` es obligatorio y único, y puede utilizarse también como
 * dato de contacto o login.
 */

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

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

    @Column(unique = true, nullable = false)
    private String email;

    // Implementación obligatoria para UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role); // asumiendo que Role implementa GrantedAuthority
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // no controlamos expiración
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // no controlamos bloqueo
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // no controlamos expiración de credenciales
    }

    @Override
    public boolean isEnabled() {
        return true; // asumimos que todos los usuarios están habilitados
    }
}
