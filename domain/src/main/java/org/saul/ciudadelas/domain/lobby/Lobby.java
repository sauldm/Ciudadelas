package org.saul.ciudadelas.domain.lobby;

import org.saul.ciudadelas.domain.exception.ExpectedGameError;
import org.saul.ciudadelas.domain.game.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Lobby {

    private UUID id;
    private List<Player> players = new ArrayList<>();

    public Lobby(UUID lobbyId) {
        this.id = lobbyId;
    }

    public void addPlayer(Player player) {
        if (arePlayersCompleted()) throw new ExpectedGameError("No puede haber mas de dos jugadores");
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public List<Player> getPlayers() {
        return List.copyOf(players);
    }

    public boolean arePlayersCompleted(){
        return players.size() >= 2;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Lobby{" +
                "id=" + id +
                ", players=" + players +
                '}';
    }
}
