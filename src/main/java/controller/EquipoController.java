package controller;

import com.torneo.api.entities.EquipoEntity;
import com.torneo.api.services.EquipoService;
import com.torneo.api.services.JugadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
@RequiredArgsConstructor
public class EquipoController {
    private final EquipoService equipoService;

    @GetMapping
    public List<EquipoEntity> listarEquipos(){
        return equipoService.listarEquipos();
    }

    @GetMapping ("/{id}")
    public EquipoEntity buscarEquipoPorId(@PathVariable Integer id){
        return equipoService.buscarEquipoPorId(id);
    }

    @PostMapping
    public EquipoEntity crearEquipo(@RequestBody EquipoEntity equipo){
        return equipoService.crearEquipo(equipo);
    }

    @DeleteMapping("/{id}")
    public void eliminarEquipo(@PathVariable Integer id){
        equipoService.eliminarEquipo(id);
    }

    @GetMapping
    public List<EquipoEntity> listarEquiposPorTorneo(@RequestParam Integer torneoId) {
        return equipoService.filtrarEquiposPorTorneoId(torneoId);
    }

    @PostMapping("/{id}")
    public EquipoEntity actualizarEquipo(@PathVariable Integer id, @RequestBody EquipoEntity equipo) {
        equipo.setId(id);
        return equipoService.crearEquipo(equipo);
    }

}
