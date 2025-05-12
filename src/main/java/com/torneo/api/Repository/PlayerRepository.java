package com.torneo.api.Repository;

import com.torneo.api.Models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Reseña:
 * Esta interfaz extiende JpaRepository para acceder a los datos de los jugadores (Player).
 * Permite usar métodos CRUD sin escribir consultas SQL manuales.
 */

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    // agregar métodos si es necesario
}
