package com.torneo.api.Services;

import com.torneo.api.Models.Player;
import com.torneo.api.Repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
 * Reseña:
 * Esta clase maneja la lógica de negocio relacionada con los jugadores.
 * Proporciona métodos para obtener todos los jugadores, buscar por ID,
 * guardar nuevos jugadores y eliminar existentes.
 */

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
    }


    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }
}
