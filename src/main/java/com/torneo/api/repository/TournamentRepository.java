package com.torneo.api.repository;

import com.torneo.api.enums.GamesCategory;
import com.torneo.api.enums.GamesState;
import com.torneo.api.models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
