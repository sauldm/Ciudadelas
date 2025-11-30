package org.saul.ciudadelas.ports;

import org.saul.ciudadelas.domain.game.players.Player;

import java.util.List;

public interface PlayerRepositoryPort {
    List<Player> findAllPlayers();
}
