package org.saul.ciudadelas.out.mapper;

import org.saul.ciudadelas.domain.game.players.Player;
import org.saul.ciudadelas.domain.lobby.Lobby;
import org.saul.ciudadelas.out.entity.LobbyEntity;
import org.saul.ciudadelas.out.entity.PlayerEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LobbyMapper {

    private final PlayerMapper playerMapper;

    public LobbyMapper(PlayerMapper playerMapper) {
        this.playerMapper = playerMapper;
    }

    public Lobby toDomain(LobbyEntity entity) {

        List<Player> players = entity.getPlayers()
                .stream()
                .map(playerMapper::toDomain)
                .toList();
        Lobby lobby = new Lobby(entity.getId());
        players.forEach(lobby::addPlayer);

        return lobby;
    }

    public LobbyEntity toEntity(Lobby lobby) {
        LobbyEntity entity = new LobbyEntity();
        entity.setId(lobby.getId());

        List<PlayerEntity> playerEntities = lobby.getPlayers().stream()
                .map((player -> {
                    return playerMapper.toEntity(player.getId(), player.getNickName());
                }))
                .toList();

        playerEntities.forEach(p -> p.setLobby(entity));
        entity.setPlayers(playerEntities);


        return entity;
    }
}

