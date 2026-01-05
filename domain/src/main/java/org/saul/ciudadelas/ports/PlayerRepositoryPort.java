package org.saul.ciudadelas.ports;

import org.saul.ciudadelas.domain.game.PlayerClassification;
import org.saul.ciudadelas.domain.game.players.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerRepositoryPort {
    Optional<Player> findById(Long id);
    List<Player> findAllPlayers();
    Optional<Player> findByName(String name);
    Player save(Player player);
    List<PlayerClassification> findAllByOrderByGamesWonDesc();

}
