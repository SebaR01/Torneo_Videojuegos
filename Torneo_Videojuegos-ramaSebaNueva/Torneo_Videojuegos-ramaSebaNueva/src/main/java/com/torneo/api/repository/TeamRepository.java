package com.torneo.api.repository;

import com.torneo.api.models.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {

    // Buscar todos los equipos asociados a un torneo por ID
    List<TeamEntity> findByTournament_Id(Long tournamentId);
    //Buscar por id

}
