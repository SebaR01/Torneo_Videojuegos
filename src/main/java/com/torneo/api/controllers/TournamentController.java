package com.torneo.api.controllers;


import com.torneo.api.dto.TournamentCreateDTO;
import com.torneo.api.dto.TournamentDTO;
import com.torneo.api.enums.GamesCategory;
import com.torneo.api.enums.GamesState;
import com.torneo.api.services.TournamentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Torneos", description = "API para gestionar y consultar torneos") //OPEN API
@RestController
@RequestMapping(value = "/api/tournament")
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @Operation(
            summary = "Crear un nuevo torneo",
            description = "Crea un torneo con los datos proporcionados en el cuerpo de la solicitud. Requiere rol de administrador."
    )//OPEN API
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Torneo creado con éxito",
                    content = @Content(schema = @Schema(implementation = TournamentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado: se requiere rol de administrador")
    }) //OPEN API
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TournamentDTO> createTournament(
            @Parameter(description = "Datos del torneo a crear", required = true) //OPEN API
            @Valid @RequestBody TournamentCreateDTO tournamentCreateDTO) {
        return ResponseEntity.ok(tournamentService.createTournament(tournamentCreateDTO));
    }

    @Operation(
            summary = "Obtener todos los torneos",
            description = "Devuelve una lista de todos los torneos disponibles en el sistema."
    )//OPEN API
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de torneos obtenida con éxito",
                    content = @Content(schema = @Schema(implementation = TournamentDTO.class))),
            @ApiResponse(responseCode = "204", description = "No hay torneos disponibles")
    }) //OPEN API
    @GetMapping
    public ResponseEntity<List<TournamentDTO>> getAll() {
        return ResponseEntity.ok(tournamentService.getAll());
    }

    @Operation(
            summary = "Actualizar un torneo por ID",
            description = "Actualiza los datos de un torneo existente identificado por su ID. Requiere rol de administrador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Torneo actualizado con éxito",
                    content = @Content(schema = @Schema(implementation = TournamentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado: se requiere rol de administrador"),
            @ApiResponse(responseCode = "404", description = "Torneo no encontrado")
    }) //OPEN API
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TournamentDTO> updateTournament(
            @Parameter(description = "ID del torneo a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados del torneo", required = true)
            @Valid @RequestBody TournamentCreateDTO tournamentCreateDTO) {
        return ResponseEntity.ok(tournamentService.updateTournament(id, tournamentCreateDTO));
    }

    @Operation(
            summary = "Obtener un torneo por ID",
            description = "Devuelve los detalles de un torneo específico identificado por su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Torneo encontrado con éxito",
                    content = @Content(schema = @Schema(implementation = TournamentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Torneo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TournamentDTO> getById(
            @Parameter(description = "ID del torneo a obtener", required = true, example = "1")
            @PathVariable Long id) {
        return tournamentService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Obtener torneos por estado",
            description = "Devuelve una lista de torneos filtrados por su estado (por ejemplo, PLANIFICADO, EN_CURSO, COMPLETADO, CANCELADO)."
    )//OPEN API
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de torneos encontrada con éxito",
                    content = @Content(schema = @Schema(implementation = TournamentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Estado inválido proporcionado"),
            @ApiResponse(responseCode = "204", description = "No se encontraron torneos para el estado especificado")
    })//OPEN API
    @GetMapping("/get/state")
    public ResponseEntity<List<TournamentDTO>> getAllByState(
            @Parameter(description = "Estado del torneo (por ejemplo, PLANIFICADO, EN_CURSO)", required = true, example = "EN_CURSO")
            @RequestParam GamesState gamesState) {
        return ResponseEntity.ok(tournamentService.getAllByState(gamesState));
    }

    @Operation(
            summary = "Obtener torneos por categoría",
            description = "Devuelve una lista de torneos filtrados por su categoría de juego (por ejemplo, FPS, MOBA, ESTRATEGIA)."
    )//OPEN API
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de torneos encontrada con éxito",
                    content = @Content(schema = @Schema(implementation = TournamentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Categoría inválida proporcionada"),
            @ApiResponse(responseCode = "204", description = "No se encontraron torneos para la categoría especificada")
    })//OPEN API
    @GetMapping("/get/category")
    public ResponseEntity<List<TournamentDTO>> getAllByCategory(
            @Parameter(description = "Categoría del juego (por ejemplo, FPS, MOBA)", required = true, example = "MOBA")
            @RequestParam GamesCategory gameCategory) {
        return ResponseEntity.ok(tournamentService.getAllByCategory(gameCategory));
    }

    @Operation(
            summary = "Eliminar un torneo por ID",
            description = "Elimina un torneo específico identificado por su ID. Requiere rol de administrador."
    )//OPEN API
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Torneo eliminado con éxito"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado: se requiere rol de administrador"),
            @ApiResponse(responseCode = "404", description = "Torneo no encontrado")
    })//OPEN API
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTournament(
            @Parameter(description = "ID del torneo a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        tournamentService.deleteTournament(id);
        return ResponseEntity.noContent().build();
    }
}