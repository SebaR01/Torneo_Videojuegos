package com.torneo.api.repository;

import com.torneo.api.models.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para acceder a jugadores.
 * Permite búsquedas por equipo y torneo.
 */
@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> { // CAMBIO: ID ahora es Long

    @Query("SELECT p FROM PlayerEntity p JOIN p.teams t WHERE t.id = :teamId")
    List<PlayerEntity> findPlayersByTeamId(@Param("teamId") Long teamId); // CAMBIO

    @Query("SELECT DISTINCT p FROM PlayerEntity p JOIN p.teams t WHERE t.tournamentId = :tournamentId")
    List<PlayerEntity> findPlayerByTournamentId(@Param("tournamentId") Long tournamentId); // CAMBIO
}
