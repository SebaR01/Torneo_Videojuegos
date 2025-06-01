package com.torneo.api.controllers;

import com.torneo.api.dto.TournamentDTO;
import com.torneo.api.dto.TournamentXTeamCreateDTO;
import com.torneo.api.dto.TournamentXTeamDTO;
import com.torneo.api.services.TournamentXTeamService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//Con esa anotación, proporcionada por el módulo de Spring Spring MVC, el contenedor ya sabe que la clase es un Bean. Es la anotación usada en una APIRest. Devuelve un archivo Json, en vez de un HTML como  lo haría un @Controller. Por ello es que uso a anotación @RestController y no @Controller
@RequestMapping(value = "/api/tournamentXteam")
public class TournamentXTeamController
{
    @Autowired
    private TournamentXTeamService tournamentXTeamService;

    @PostMapping
    @PreAuthorize("hasRole('JUGADOR')")
    public ResponseEntity<TournamentXTeamDTO> createTXT(@Valid @RequestBody TournamentXTeamCreateDTO TXTCDTO)
    {
        return ResponseEntity.ok(tournamentXTeamService.createTournamentXteam(TXTCDTO));
    }

    @GetMapping
    public ResponseEntity<List<TournamentXTeamDTO>> getAll()
    {
        return ResponseEntity.ok(tournamentXTeamService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTXT(@Parameter(description = "ID del TXT a eliminar", required = true, example = "3") @PathVariable Long id)
    {
        tournamentXTeamService.deleteTournamentxTeam(id);
        return ResponseEntity.noContent().build();
    }
}
