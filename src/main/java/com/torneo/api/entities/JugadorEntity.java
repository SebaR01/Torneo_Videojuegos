package com.torneo.api.entities;

import java.util.List;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class JugadorEntity {
    private Integer id;
    private String nickname;
    private String nombre;
    private String apellido;
    private String email;
    private Integer edad;
    private List <EquipoEntity> equipos;


}
