package com.torneo.api.Controllers;

import com.torneo.api.DTO.ResultCreateDTO;
import com.torneo.api.DTO.ResultDTO;
import com.torneo.api.Services.ResultService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Con esa anotación, proporcionada por el módulo de Spring Spring MVC, el contenedor ya sabe que la clase es un Bean. Es la anotación usada en una APIRest. Devuelve un archivo Json, en vez de un HTML como  lo haría un @Controller.
@RequestMapping(value = "/api/results") //Anotación proporcionada por el módulo de Spring Spring MVC. RequestMapping a nivel clase. Establece la ruta base de todos los métodos de este controlador. O sea, todos los métodos de este controlador tendrán como ruta base /api/results. Todos los endpoint tendrán esa ruta base. Luego, cambia el método por delante: GET, POST... Por ejemplo, un método con la anotación PostMapping tendrá como ruta o endpoint Post/api/results...
//Es que la anotación @RequestMapping a nivel clase se usa para definir la ruta común a todos los endpoints. Luego, en cada método particular de la clase, se definirá el método usando las anotaciones abreviadas de @RequestMapping. Por ejemplo, @GetMapping.
public class ResultController
{
    @Autowired
    private ResultService resultService;

    @PostMapping //Anotación proporcionada por Spring MVC. Acordate que el método Post es para crear
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResultDTO> creatResult(@Valid @RequestBody ResultCreateDTO resultCreateDTO) { //@Valid lo que hace es comprobar que el objeto resultCreateDTO cuente con las validaciones definidas por las anotaciones, que me proporciona la API que nos da Jakarta Validation (por ejemplo, el size, los campos obligatorios...). @RequestBody, anotación proporcionada por el módulo de Spring Spring MVC, deserializa lo que se envía a la solicitud http (una instancia de ResultCreateDTO, en este caso).
        return ResponseEntity.ok(resultService.createResult(resultCreateDTO));
    }

    @GetMapping //El método Get es para obtener
    public ResponseEntity<List<ResultDTO>> getAll()
    {
        return ResponseEntity.ok(resultService.getAll());
    }

    @PutMapping("/{id}") //El método PUT es para actualizar.
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResultDTO> updateResult (@PathVariable Long id, @Valid @RequestBody ResultCreateDTO resultCreateDTO)
    {
        return ResponseEntity.ok(resultService.updateResult(id, resultCreateDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultDTO> getById(@PathVariable Long id) //La anotación PathVariable, proporcionada por el módulo de Spring Spring MVC, lo que hace es capturar el valor de la variable id (la cual es la que aparece en GetMapping) y la transforma a tipo Long. Pues, llega en formato String. El método usa ese id para buscar el result deseado.
    {
        return resultService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}") //El método Delete es para borrar.
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteResult(@PathVariable Long id) {
        resultService.deleteResult(id);
        return ResponseEntity.noContent().build();
    }

}
