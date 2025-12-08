package services;

import org.saul.ciudadelas.domain.game.players.Player;
import org.saul.ciudadelas.ports.PlayerRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class PlayerService {

    private final PlayerRepositoryPort playerRepositoryPort;


    public PlayerService(PlayerRepositoryPort playerRepositoryPort) {
        this.playerRepositoryPort = playerRepositoryPort;
    }

    public Player getOrCreatePlayer(String nickName) {

        Player player = playerRepositoryPort.save(nickName);

        return player;
    }
}
