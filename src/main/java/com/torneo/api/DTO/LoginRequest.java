package com.torneo.api.DTO;

import lombok.Data;
/*
 * Reseña:
 * Este DTO representa la solicitud de inicio de sesión (login).
 * Contiene el nombre de usuario y la contraseña que el usuario envía al sistema para autenticarse.
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
}
