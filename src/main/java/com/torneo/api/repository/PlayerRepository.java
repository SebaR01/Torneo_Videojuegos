package com.torneo.api.repository;


import com.torneo.api.models.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository <PlayerEntity, Integer> {

    @Query("SELECT p FROM PlayerEntity p JOIN p.teams t WHERE t.id = :teamId")
    List<PlayerEntity> findPlayersByTeamId(@Param("teamId") Integer teamId);

    @Query("SELECT DISTINCT p FROM PlayerEntity p JOIN p.teams t WHERE t.tournamentId = :tournamentId")
    List<PlayerEntity> findPlayerByTournamentId(@Param("tournamentId") Integer tournamentId);


}