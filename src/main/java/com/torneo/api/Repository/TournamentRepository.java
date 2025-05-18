package com.torneo.api.Repository;

import ENUMS.GamesCategory;
import ENUMS.GamesState;
import com.torneo.api.Models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long>
{
    //Obtener todos los torneos por algún filtro: estado, fecha y categoría;
    public List<Tournament> findByStateContainingIgnoreCase(GamesState gamesState);
    public List<Tournament> findByCategoryContainingIgnoreCase(GamesCategory gamesCategory);

}
