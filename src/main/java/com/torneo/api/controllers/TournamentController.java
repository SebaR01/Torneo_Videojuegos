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
@RestController  //Con esa anotación, proporcionada por el módulo de Spring Spring MVC, el contenedor ya sabe que la clase es un Bean. Es la anotación usada en una APIRest. Devuelve un archivo Json, en vez de un HTML como  lo haría un @Controller. Por ello es que uso a anotación @RestController y no @Controller
@RequestMapping(value = "/api/tournament") //Anotación proporcionada por el módulo de Spring Spring MVC. RequestMapping a nivel clase. Establece la ruta base de todos los métodos de este controlador. O sea, todos los métodos de este controlador tendrán como ruta base /api/tournament. Todos los endpoint tendrán esa ruta base. Luego, cambia el método por delante: GET, POST... Por ejemplo, un método con la anotación PostMapping tendrá como ruta o endpoint Post/api/tournament...
//Es que la anotación @RequestMapping a nivel clase se usa para definir la ruta común a todos los endpoints. Luego, en cada método particular de la clase, se definirá el método usando las anotaciones abreviadas de @RequestMapping. Por ejemplo, @GetMapping.
public class TournamentController {

    @Autowired //Anotación, que ya viene en el corazón del framework de Spring,usado para inyectar una dependencia. TournamentService, al tener la anotación @Service, ya sabe que es un Bean.
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
    @PostMapping //Anotación proporcionada por Spring MVC. Acordate que el método Post es para crear. Se agrega el POST adelante de todo el endpoint.
   // @PreAuthorize("hasRole('ADMIN')") //Anotación proporcionada por la librería Spring Security. Para usarla, tuve que inyectar la dependencia. Hace que para poder acceder a este endpoint el usuario logueado deba tener el rol 'ADMIN'; sino, tira el error 403.
    public ResponseEntity<TournamentDTO> createTournament(
            @Parameter(description = "Datos del torneo a crear", required = true) //OPEN API
            @Valid @RequestBody TournamentCreateDTO tournamentCreateDTO) {
        return ResponseEntity.ok(tournamentService.createTournament(tournamentCreateDTO));
    }      //Método.
    //@Valid lo que hace es comprobar que el objeto tournamentCreateDTO cuente con las validaciones definidas por las anotaciones, que me proporciona la API que nos da Jakarta Validation, en la clase TournamentCreateDTO (por ejemplo, el size, los campos obligatorios...).
    // @RequestBody, anotación proporcionada por el módulo de Spring Spring MVC, deserializa lo que se envía a la solicitud http (una instancia de TournamentCreateDTO, en este caso).
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
    public ResponseEntity<List<TournamentDTO>> getAll()
    {
        return ResponseEntity.ok(tournamentService.getAll());
    } //Método

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
    } //Método

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