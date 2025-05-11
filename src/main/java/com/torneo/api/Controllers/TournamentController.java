package com.torneo.api.Controllers;

import com.torneo.api.DTO.TournamentCreateDTO;
import com.torneo.api.DTO.TournamentDTO;
import com.torneo.api.Services.ResultService;
import com.torneo.api.Services.TournamentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

    @DeleteMapping("/{id}") //El método Delete es para borrar.
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTournament(@PathVariable Long id) {
        tournamentService.deleteTournament(id);
        return ResponseEntity.noContent().build();
    }
}
