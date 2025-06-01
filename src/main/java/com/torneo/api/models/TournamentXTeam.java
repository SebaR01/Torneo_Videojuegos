package com.torneo.api.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tournamentXteam")
@Data
public class TournamentXTeam
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany
    @JoinColumn(name = "tournamentId") //foraign key
    private Tournament tournamentId;

    @OneToMany
    @JoinColumn(name = "teamId")
    private EquipoEntity equipoEntityId;

}
