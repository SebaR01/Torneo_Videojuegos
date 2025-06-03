package com.torneo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Este DTO se usa para devolver el token JWT al usuario después de iniciar sesión.
 * Solo contiene el token generado.
 */
@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
}
