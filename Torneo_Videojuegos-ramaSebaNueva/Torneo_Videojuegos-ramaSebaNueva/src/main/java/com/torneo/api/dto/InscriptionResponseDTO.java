/**
 * DTO de salida para mostrar una inscripción ya registrada.
 *
 * ✔ Incluye el nombre del equipo y del torneo.
 * ✔ Muestra la fecha de inscripción y el costo.
 * ✔ Se utiliza para retornar los datos al frontend.
 */

package com.torneo.api.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InscriptionResponseDTO {

    private Long id;

    private int teamID;

    private int tournamentID;

    private LocalDate date;

    private Double cost;
}
