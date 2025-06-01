package com.torneo.api.dto;

import com.torneo.api.Models.EquipoEntity;
import com.torneo.api.Models.Tournament;

import jakarta.persistence.*;
import lombok.Data;

@Data
public class TournamentXTeamCreateDTO
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany
    @JoinColumn(name = "tournamentId") //foraign key
    private Tournament tournamentId;

    private EquipoEntity equipoEntityId;
}
