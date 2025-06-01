package com.torneo.api.repository;

import com.torneo.api.Models.Tournament;
import com.torneo.api.Models.TournamentXTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentXTeamRepository extends JpaRepository<TournamentXTeam, Integer>
{
    //Traerme registros por el id del team
    public List<TournamentXTeam> findByequipoEntityId_id(int equipoEntityId);


}
