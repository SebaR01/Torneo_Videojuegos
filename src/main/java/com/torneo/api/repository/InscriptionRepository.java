package com.torneo.api.repository;

import com.torneo.api.models.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio que permite acceder y gestionar inscripciones almacenadas en la base de datos.
 * Hereda de JpaRepository para operaciones CRUD automáticas.
 */
// En InscriptionRepository.java
@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    boolean existsByTeamIdAndTournamentId(Long teamId, Long tournamentId);
}
