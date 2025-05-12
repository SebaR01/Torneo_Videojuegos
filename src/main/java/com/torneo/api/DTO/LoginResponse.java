package com.torneo.api.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
/*
 * Reseña:
 * Este DTO representa la respuesta que el sistema devuelve al usuario después de un login exitoso.
 * Contiene el token JWT generado, que el usuario usará para autenticarse en los siguientes pedidos.
 */
@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
}
