package com.torneo.api.Models;

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

    @ManyToOne
    @JoinColumn(name = "tournamentId") //foraign key
    private Tournament tournamentId;

    @ManyToOne
    @JoinColumn(name = "teamId")
    private EquipoEntity equipoEntityId;

}
