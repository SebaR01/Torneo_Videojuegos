package com.torneo.api.Services;

import com.torneo.api.DTO.TournamentCreateDTO;
import com.torneo.api.DTO.TournamentDTO;
import com.torneo.api.Models.Tournament;
import com.torneo.api.Repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TournamentService
{
    @Autowired //Inyecto la dependencia.
    private TournamentRepository tournamentRepository;

    public TournamentDTO createTournament(TournamentCreateDTO tournamentCreateDTO)
    {
        Tournament tournament = new Tournament();
        return null;
    }
}
