package com.torneo.api.repository;
import com.torneo.api.models.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface MatchRepository extends JpaRepository<MatchEntity, Integer> {
    List<MatchEntity> findByTournamentId(Integer tournamentId);
}