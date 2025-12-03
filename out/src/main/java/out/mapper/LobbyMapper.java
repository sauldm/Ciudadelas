package out.mapper;

import org.saul.ciudadelas.domain.game.players.Player;
import org.saul.ciudadelas.domain.lobby.Lobby;
import out.entity.LobbyEntity;
import out.entity.PlayerEntity;

import java.util.List;

public class LobbyMapper {

    private final PlayerMapper playerMapper = new PlayerMapper();

    public Lobby toDomain(LobbyEntity entity) {

        List<Player> players = entity.getPlayers()
                .stream()
                .map(playerMapper::toDomain)
                .toList();
        Lobby lobby = new Lobby(entity.getId());
        players.forEach(lobby::addPlayer);

        return lobby;
    }

    public LobbyEntity toEntity(Lobby lobby, List<PlayerEntity> playerEntities) {
        LobbyEntity entity = new LobbyEntity();
        entity.setId(lobby.getId());
        entity.getPlayers().addAll(playerEntities);
        playerEntities.forEach(p -> p.setLobby(entity));
        return entity;
    }
}

