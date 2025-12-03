package org.saul.ciudadelas.ports;

import org.saul.ciudadelas.domain.game.players.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerRepositoryPort {
    Optional<Player> findById(UUID id);
    List<Player> findAllPlayers();
    void save(Player player);

}
