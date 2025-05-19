package com.torneo.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "results", schema = "public")
@Data
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "You must add a score. Must.")
    @Min(value = 0, message = "min value is 0.")
    private Long scoreWinnerTeam;

    @NotNull
    @Min(value = 0, message = "min value is 0.")
    private Long scoreLoserTeam;

    @OneToOne
    @JoinColumn(name = "tournament_id") // Cambia a una columna única
    private Tournament tournamentId;

    @OneToOne
    @JoinColumn(name = "loser_team_id") // Cambia a una columna única
    private Team loserTeamId;

    @OneToOne
    @JoinColumn(name = "winner_team_id") // Cambia a una columna única
    private Team winerTeamId; // Nota: Corrige el typo "winer" a "winner"
}