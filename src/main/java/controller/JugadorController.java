package controller;

import com.torneo.api.entities.JugadorEntity;
import com.torneo.api.services.JugadorService;
import jdk.dynalink.linker.LinkerServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jugadores")
@RequiredArgsConstructor
public class JugadorController {

    private final JugadorService jugadorService;

    @GetMapping
    public List<JugadorEntity> listarJugadores(){
        return jugadorService.listarJugadores();
    }

    @GetMapping ("/{id}")
    public JugadorEntity buscarJugadorPorId(@PathVariable Integer id){
        return jugadorService.buscarJugadorPorId(id);
    }

    @PostMapping
    public JugadorEntity crearJugador(@RequestBody JugadorEntity jugador){
        return jugadorService.crearJugador(jugador);
    }

    @DeleteMapping("/{id}")
    public void eliminarJugador(@PathVariable Integer id){
        jugadorService.eliminarJugador(id);
    }

    @GetMapping
    public List<JugadorEntity> listarJugadoresPorEquipo(@RequestParam Integer equipoId) {
        return jugadorService.listarJugadoresPorEquipo(equipoId);
    }

    @GetMapping
    public List<JugadorEntity> listarJugadoresPorTorneo(@RequestParam Integer torneoId) {
        return jugadorService.listarJugadoresPorTorneo(torneoId);
    }

    @PostMapping("/{id}")
    public JugadorEntity actualizarJugador(@PathVariable Integer id, @RequestBody JugadorEntity jugador) {
        jugador.setId(id);
        return jugadorService.crearJugador(jugador);
    }



}
