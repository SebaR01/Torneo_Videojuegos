package com.torneo.api.enums;

import org.springframework.security.core.GrantedAuthority;

/**
 * Este enum define los distintos roles posibles que puede tener un usuario en el sistema:
 * - ADMIN → acceso completo, incluyendo creación y eliminación.
 * - ORGANIZADOR → puede gestionar equipos, jugadores y torneos.
 * - JUGADOR → puede ver datos, inscribirse y crear equipos propios.
 *
 * Cada valor del enum actúa como una autoridad dentro de Spring Security,
 * gracias a que implementa la interfaz `GrantedAuthority`. Esto permite usarlo
 * directamente en validaciones como `@PreAuthorize("hasRole('ADMIN')")`.
 */
public enum Role implements GrantedAuthority {
    ADMIN,
    ORGANIZADOR,
    JUGADOR;

    @Override
    public String getAuthority() {
        return "ROLE_" + name(); // ✅ esto es lo que espera Spring Security
    }
}
