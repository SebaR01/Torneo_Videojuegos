package com.torneo.api.Repository;

import com.torneo.api.enums.GamesCategory;
import com.torneo.api.enums.GamesState;
import com.torneo.api.Models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long>
{
    //Dado que, al extender de Jpa, tengo acceso todos los métodos básicos del CRUD, solo debo escribir la firma de aquellos métodos más personalizados como buscar a un libro por su autor, por su título o por si está disponible.
    List<Tournament> findByNameContainingIgnoreCase(String name);
    List<Tournament> findByGameContainingIgnoreCase(String game);
    List<Tournament> findByGamesState(GamesState gamesState);
    List<Tournament> findByGamesCategory(GamesCategory gamesCategory);
}
