package com.torneo.api.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EquipoEntity {
    private Integer id;
    private String nombre;
    private Integer torneoId;
    private List <JugadorEntity> jugadores;


}

