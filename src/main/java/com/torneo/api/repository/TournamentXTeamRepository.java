package com.torneo.api.repository;

import com.torneo.api.models.TournamentXTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentXTeamRepository extends JpaRepository<TournamentXTeam, Long>
{
}
