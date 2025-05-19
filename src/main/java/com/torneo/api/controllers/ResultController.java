package com.torneo.api.controllers;

import com.torneo.api.dto.ResultCreateDTO;
import com.torneo.api.dto.ResultDTO;
import com.torneo.api.services.ResultService;
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

/**
 * Controlador REST para gestionar resultados de torneos.
 * Proporciona endpoints para crear, consultar, actualizar y eliminar resultados.
 */
@Tag(name = "Resultados", description = "API para gestionar y consultar resultados de torneos") //OPEN API
@RestController
@RequestMapping(value = "/api/results")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @Operation(
            summary = "Crear un nuevo resultado",
            description = "Crea un resultado de torneo con los datos proporcionados en el cuerpo de la solicitud. Requiere rol de administrador."
    )//OPEN API
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado creado con éxito",
                    content = @Content(schema = @Schema(implementation = ResultDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado: se requiere rol de administrador")
    })//OPEN API
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResultDTO> createResult(
            @Parameter(description = "Datos del resultado a crear", required = true)
            @Valid @RequestBody ResultCreateDTO resultCreateDTO) {
        return ResponseEntity.ok(resultService.createResult(resultCreateDTO));
    }

    @Operation(
            summary = "Obtener todos los resultados",
            description = "Devuelve una lista de todos los resultados de torneos disponibles en el sistema."
    )//OPEN API
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de resultados obtenida con éxito",
                    content = @Content(schema = @Schema(implementation = ResultDTO.class))),
            @ApiResponse(responseCode = "204", description = "No hay resultados disponibles")
    })//OPEN API
    @GetMapping
    public ResponseEntity<List<ResultDTO>> getAll() {
        return ResponseEntity.ok(resultService.getAll());
    }

    @Operation(
            summary = "Actualizar un resultado por ID",
            description = "Actualiza los datos de un resultado existente identificado por su ID. Requiere rol de administrador."
    )//OPEN API
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado actualizado con éxito",
                    content = @Content(schema = @Schema(implementation = ResultDTO.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado: se requiere rol de administrador"),
            @ApiResponse(responseCode = "404", description = "Resultado no encontrado")
    })//OPEN API
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResultDTO> updateResult(
            @Parameter(description = "ID del resultado a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados del resultado", required = true)
            @Valid @RequestBody ResultCreateDTO resultCreateDTO) {
        return ResponseEntity.ok(resultService.updateResult(id, resultCreateDTO));
    }

    @Operation(
            summary = "Obtener un resultado por ID",
            description = "Devuelve los detalles de un resultado específico identificado por su ID."
    )//OPEN API
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado encontrado con éxito",
                    content = @Content(schema = @Schema(implementation = ResultDTO.class))),
            @ApiResponse(responseCode = "404", description = "Resultado no encontrado")
    })//OPEN API
    @GetMapping("/{id}")
    public ResponseEntity<ResultDTO> getById(
            @Parameter(description = "ID del resultado a obtener", required = true, example = "1")
            @PathVariable Long id) {
        return resultService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Eliminar un resultado por ID",
            description = "Elimina un resultado específico identificado por su ID. Requiere rol de administrador."
    )//OPEN API
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Resultado eliminado con éxito"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado: se requiere rol de administrador"),
            @ApiResponse(responseCode = "404", description = "Resultado no encontrado")
    } ) //OPEN API
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteResult(
            @Parameter(description = "ID del resultado a eliminar", required = true, example = "1")
            @PathVariable Long id) {
        resultService.deleteResult(id);
        return ResponseEntity.noContent().build();
    }
}