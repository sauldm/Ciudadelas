package org.saul.ciudadelas.ports;

import org.saul.ciudadelas.domain.game.players.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerRepositoryPort {
    Optional<Player> findById(Long id);
    List<Player> findAllPlayers();
    void save(Player player);

}
