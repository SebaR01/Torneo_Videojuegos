package com.torneo.api.services;

import com.torneo.api.dto.InscriptionResponseDTO;
import com.torneo.api.dto.MatchResponseDTO;
import com.torneo.api.dto.ResultCreateDTO;
import com.torneo.api.dto.ResultDTO;
import com.torneo.api.enums.GamesState;
import com.torneo.api.exceptions.NotFoundException;
import com.torneo.api.models.*;
import com.torneo.api.repository.ResultRepository;
import com.torneo.api.repository.TeamRepository;
import com.torneo.api.repository.TournamentRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio que gestiona la creación, edición, búsqueda y eliminación de resultados.
 * Cada resultado representa un enfrentamiento entre dos equipos dentro de un torneo.
 */
@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;
    @Autowired
    private TeamXPlayerService teamXPlayerService;
    private final TournamentRepository tournamentRepository;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    private final TeamRepository teamRepository;
    @Autowired
    private InscriptionService inscriptionService;
    @Autowired
    private MatchService matchService;
    @Autowired
    private TournamentService tournamentService;
    @Autowired
    private EmailService emailService;

    public ResultDTO createResult(ResultCreateDTO dto) {
        Tournament tournament = tournamentRepository.findById(dto.getTournamentId())
                .orElseThrow(() -> new NotFoundException("Torneo no encontrado"));

        TeamEntity winner = teamRepository.findById(dto.getWinerTeamId())
                .orElseThrow(() -> new NotFoundException("Equipo ganador no encontrado"));

        TeamEntity loser = teamRepository.findById(dto.getLoserTeamId())
                .orElseThrow(() -> new NotFoundException("Equipo perdedor no encontrado"));

        Result result = Result.builder()
                .tournament(tournament)
                .winnerTeam(winner)
                .loserTeam(loser)
                .scoreWinnerTeam(dto.getScoreWinnerTeam())
                .scoreLoserTeam(dto.getScoreLoserTeam())
                .build();

        //Ahora, voy a eliminar al equipo perdedor de la tabla intermedia (inscriptions):
        Inscription inscription = inscriptionService.getInscriptionByTeamAndTournament(dto.getLoserTeamId(), dto.getTournamentId()); //Ya tengo el registro
        inscriptionService.delete(inscription.getId()); //Borro el registro del perdedor del partido. Ya no es parte del torneo.

        //Ahora elimino al partido de la base de datos:
        matchService.deleteMatch(result.getMatch().getId()); //Eliminado el partido

        //Ahora corroboro si hay partidos aún del torneo por registrar. Como los voy eliminando a medida que guardo el resultado, si no queda ningún partido es que hay que avanzar de fase.
        List<MatchResponseDTO> matchList = matchService.getMatchesByTournament(result.getTournament().getId()); //Tengo todos los partidos del torneoque quedan
        if(matchList.isEmpty()){
            //Me fijo cuántos equipos quedan vivos:
            List<InscriptionResponseDTO> inscriptionList = inscriptionService.getByTournament(result.getTournament().getId()); //tengo todas las inscripciones. Si hay registros, es que aún quedan equipos.
            if(inscriptionList.size() == 1){
                //Enviar un email al campeón
                Optional<Integer> teamID = inscriptionList.stream().map(i -> i.getTeamID()).findFirst();
                List<TeamXPlayer> teamXPlayers = teamXPlayerService.getByTeamId(teamID.get().longValue()); //Lista de registros de la tabla intermedia
                Optional<TeamXPlayer> tXp = teamXPlayers.stream().findAny(); //Me quedo con un solo registro de la tabla intermedia.
                Optional<User> user = userDetailsService.getById(tXp.get().getUser().getId());
                String email = user.get().getEmail(); //Ya tengo el email de uno de los usuarios del equipo campeón del torneo.
                try {
                    emailService.campeonEmail(email); //Envío el email
                } catch (MessagingException e) {
                    System.err.println("No se pudo enviar el email " + e);
                }

                //Ahora debo cambiar el estado del torneo a finalizado
                tournament.setState(GamesState.FINISHED);
                tournamentService.update(tournament); //Torneo actualizado y con nuevo estado. 
            }else{
                //Debo generar nuevos partidos de forma aleatoria.
            }
        }

        return mapToDTO(resultRepository.save(result));
    }

    public ResultDTO updateResult(Long id, ResultCreateDTO dto) {
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Resultado no encontrado"));

        Tournament tournament = tournamentRepository.findById(dto.getTournamentId())
                .orElseThrow(() -> new NotFoundException("Torneo no encontrado"));

        TeamEntity winner = teamRepository.findById(dto.getWinerTeamId())
                .orElseThrow(() -> new NotFoundException("Equipo ganador no encontrado"));

        TeamEntity loser = teamRepository.findById(dto.getLoserTeamId())
                .orElseThrow(() -> new NotFoundException("Equipo perdedor no encontrado"));

        result.setTournament(tournament);
        result.setWinnerTeam(winner);
        result.setLoserTeam(loser);
        result.setScoreWinnerTeam(dto.getScoreWinnerTeam());
        result.setScoreLoserTeam(dto.getScoreLoserTeam());

        return mapToDTO(resultRepository.save(result));
    }

    public void deleteResult(Long id) {
        resultRepository.deleteById(id);
    }

    public List<ResultDTO> getAll() {
        return resultRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ResultDTO> getById(Long id) {
        return resultRepository.findById(id).map(this::mapToDTO);
    }

    public ResultDTO mapToDTO(Result result) {
        ResultDTO dto = new ResultDTO();
        dto.setId(result.getId());
        dto.setTournamentId(result.getTournament().getId());
        dto.setWinerTeamId(result.getWinnerTeam().getId());
        dto.setLoserTeamId(result.getLoserTeam().getId());
        dto.setScoreWinnerTeam(result.getScoreWinnerTeam());
        dto.setScoreLoserTeam(result.getScoreLoserTeam());
        return dto;
    }
}
