package com.torneo.api.repository;

import com.torneo.api.Models.Tournament;
import com.torneo.api.enums.GamesCategory;
import com.torneo.api.enums.GamesState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Este repositorio permite acceder a los torneos almacenados en la base de datos.
 * Proporciona métodos automáticos para buscar por estado, categoría o fecha.
 */
@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    // Busca torneos por estado (ej: ACTIVO)
    List<Tournament> findByState(GamesState state);

    // Busca torneos por categoría (ej: SHOOTER)
    List<Tournament> findByCategory(GamesCategory category);

    // Busca torneos que empiecen en una fecha determinada
    List<Tournament> findByStartDate(Date startDate);

    @Query("SELECT t FROM Tournament t JOIN TournamentXTeam txt ON t.id = txt.tournamentId.id WHERE txt.equipoEntityId.id = :teamId")
    List<Tournament> findByTeamId(@Param("teamId") Integer teamId);
    //Objetivo: Obtener todos los torneos (Tournament) en los que está inscrito un equipo específico (identificado por teamId).
    //En TournamentRepository, define un método que realice una consulta a través de la relación muchos a muchos. Sin embargo, como la relación está en TournamentXTeam, la consulta derivada puede no ser directa. En su lugar, usaremos una consulta explícita para mayor claridad.

}
