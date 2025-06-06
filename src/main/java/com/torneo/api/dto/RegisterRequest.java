package com.torneo.api.dto;

import com.torneo.api.enums.Role;
import lombok.*;

/**
 * Este DTO se usa para registrar un nuevo usuario.
 * Incluye el nombre de usuario, la contraseña, el email y el rol.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private Role role;
}
