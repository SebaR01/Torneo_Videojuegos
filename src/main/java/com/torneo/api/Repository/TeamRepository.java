package com.torneo.api.Repository;

import com.torneo.api.Models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Reseña:
 * Este repositorio permite acceder a la base de datos de equipos (Team).
 * Al extender de JpaRepository, hereda todos los métodos CRUD básicos sin necesidad de implementarlos.
 */

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
