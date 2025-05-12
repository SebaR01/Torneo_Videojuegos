package com.torneo.api.Repository;

import com.torneo.api.Models.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Reseña:
 * Este repositorio permite acceder a los datos de las inscripciones en la base de datos.
 * Extiende de JpaRepository para disponer de métodos CRUD automáticos.
 */
@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
}
