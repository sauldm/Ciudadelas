package org.saul.ciudadelas.services;

import org.saul.ciudadelas.domain.game.PlayerClassification;
import org.saul.ciudadelas.domain.game.players.Player;
import org.saul.ciudadelas.ports.PlayerRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PlayerService {

    private final PlayerRepositoryPort playerRepositoryPort;


    public PlayerService(PlayerRepositoryPort playerRepositoryPort) {
        this.playerRepositoryPort = playerRepositoryPort;
    }

    public Player getOrCreatePlayer(String nickName) {
        Optional<Player> player = playerRepositoryPort.findByName(nickName);
        return player.orElseGet(() -> playerRepositoryPort.save(new Player(null,nickName,0)));
    }

    public Optional<Player> findByName(String nickName){
        return playerRepositoryPort.findByName(nickName);
    }

    public List<PlayerClassification> getClassificationTable(){
        return playerRepositoryPort.findAllByOrderByGamesWonDesc();
    }

}
