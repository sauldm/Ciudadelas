package services;

import org.saul.ciudadelas.domain.game.players.Player;
import org.saul.ciudadelas.domain.lobby.Lobby;
import org.saul.ciudadelas.ports.LobbyRepositoryPort;
import org.saul.ciudadelas.ports.PlayerRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LobbyService {

    private final LobbyRepositoryPort lobbyRepositoryPort;
    private final PlayerRepositoryPort playerRepositoryPort;

    public LobbyService(LobbyRepositoryPort lobbyRepositoryPort, PlayerRepositoryPort playerRepositoryPort) {
        this.lobbyRepositoryPort = lobbyRepositoryPort;
        this.playerRepositoryPort = playerRepositoryPort;
    }

    // TODO: metodo para crear lobby y guardarlo en bd despues
    public Lobby createNewLobby(UUID lobbyId) {
        Lobby lobby = new Lobby(lobbyId);
        lobbyRepositoryPort.save(lobby);
        return lobby;

    }

    // TODO: metodo para añadir jugador a lobby, guardandolo en bd despues
    public void addPlayerToLobby(Long lobbyId, Long playerId) {
        Lobby lobby = lobbyRepositoryPort.findById(lobbyId).orElseThrow(() -> new RuntimeException("Lobby not found"));
        Player player = playerRepositoryPort.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found"));
        lobby.addPlayer(player);
        lobbyRepositoryPort.save(lobby);
    }

    // TODO: metodo para eliminar jugador de lobby, guardandolo en bd despues
    public void removePlayerFromLobby(Long lobbyId, Long playerId) {
        Lobby lobby = lobbyRepositoryPort.findById(lobbyId).orElseThrow(() -> new RuntimeException("Lobby not found"));
        Player player = playerRepositoryPort.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found"));
        lobby.removePlayer(player);
        lobbyRepositoryPort.save(lobby);
    }
}
