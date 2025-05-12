package com.torneo.api.DTO;

import com.torneo.api.enums.Role;
import lombok.Data;
/*
 * Reseña:
 * Este DTO sirve para exponer los datos públicos de un usuario.
 * Se usa si queremos devolver información del usuario al frontend sin incluir la contraseña.
 */
@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private Role role;
}
