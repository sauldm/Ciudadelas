package out.mapper;

import org.saul.ciudadelas.domain.game.players.Player;
import out.entity.PlayerEntity;

public class PlayerMapper {

    public PlayerEntity toEntity(Player player) {
        PlayerEntity entity = new PlayerEntity();
        entity.setNickName(player.getNickName());
        entity.setId(player.getId());
        return entity;
    }

    public Player toDomain(PlayerEntity entity) {
        return new Player(entity.getId(), entity.getNickName());
    }
}
