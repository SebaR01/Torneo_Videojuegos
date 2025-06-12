package com.torneo.api.repository;

import com.torneo.api.models.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

    List<Inscription> findByTournamentId(Long tournamentId);

    List<Inscription> findByTeamId(Long teamId);
}
