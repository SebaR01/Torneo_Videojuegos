package com.torneo.api.dto;

import lombok.Data;

/**
 * Este DTO se usa cuando un usuario quiere iniciar sesión.
 * Contiene el nombre de usuario y la contraseña que ingresa.
 */

@Data
public class LoginRequest {
    private String username;
    private String password;
}
