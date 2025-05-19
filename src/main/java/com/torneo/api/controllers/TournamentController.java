package com.torneo.api.controllers;


import com.torneo.api.dto.TournamentCreateDTO;
import com.torneo.api.dto.TournamentDTO;
import com.torneo.api.enums.GamesCategory;
import com.torneo.api.enums.GamesState;
import com.torneo.api.services.TournamentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/tournament") //Anotación proporcionada por el módulo de Spring Spring MVC. RequestMapping a nivel clase. Establece la ruta base de todos los métodos de este controlador. O sea, todos los métodos de este controlador tendrán como ruta base /api/libros. Todos los endpoint tendrán esa ruta base. Luego, cambia el método por delante: GET, POST... Por ejemplo, un método con la anotación PostMapping tendrá como ruta o endpoint Post/api/libros...
//Es que la anotación @RequestMapping a nivel clase se usa para definir la ruta común a todos los endpoints. Luego, en cada método particular de la clase, se definirá el método usando las anotaciones abreviadas de @RequestMapping. Por ejemplo, @GetMapping.
public class TournamentController
{
    @Autowired
    private TournamentService tournamentService;

    @PostMapping //Anotación proporcionada por Spring MVC. Acordate que el método Post es para crear
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TournamentDTO> creatResult(@Valid @RequestBody TournamentCreateDTO tournamentCreateDTO) { //@Valid lo que hace es comprobar que el objeto tournamentCreateDTO cuente con las validaciones definidas por las anotaciones, que me proporciona la API que nos da Jakarta Validation (por ejemplo, el size, los campos obligatorios...). @RequestBody, anotación proporcionada por el módulo de Spring Spring MVC, deserializa lo que se envía a la solicitud http (una instancia de TournamentCreateDTO, en este caso).
        return ResponseEntity.ok(tournamentService.createTournament(tournamentCreateDTO));
    }

    @GetMapping //El método Get es para obtener
    public ResponseEntity<List<TournamentDTO>> getAll()
    {
        return ResponseEntity.ok(tournamentService.getAll());
    }

    @PutMapping("/{id}") //El método PUT es para actualizar.
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TournamentDTO> updateTournament(@PathVariable Long id, @Valid @RequestBody TournamentCreateDTO tournamentCreateDTO)
    {
        return ResponseEntity.ok(tournamentService.updateTournament(id, tournamentCreateDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentDTO> getById(@PathVariable Long id) //La anotación PathVariable, proporcionada por el módulo de Spring Spring MVC, lo que hace es capturar el valor de la variable id (la cual es la que aparece en GetMapping) y la transforma a tipo Long. Pues, llega en formato String. El método usa ese id para buscar el result deseado.
    {
        return tournamentService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/get/state")
    public ResponseEntity<List<TournamentDTO>> getAllByState(@RequestParam GamesState gamesState)
    {
        return ResponseEntity.ok(tournamentService.getAllByState(gamesState));
    }
    @GetMapping("/get/category")
    public ResponseEntity<List<TournamentDTO>> getAllByCategory(@RequestParam GamesCategory gameCategory) {
        return ResponseEntity.ok(tournamentService.getAllByCategory(gameCategory));
    }

    @DeleteMapping("/{id}") //El método Delete es para borrar.
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTournament(@PathVariable Long id) {
        tournamentService.deleteTournament(id);
        return ResponseEntity.noContent().build();
    }
}