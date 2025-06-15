package com.torneo.api.repository;

import com.torneo.api.models.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    // Jugadores de un equipo específico
    List<PlayerEntity> findByTeamId(Long teamId);

}
