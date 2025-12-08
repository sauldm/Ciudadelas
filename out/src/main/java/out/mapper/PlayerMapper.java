package out.mapper;

import org.saul.ciudadelas.domain.game.players.Player;
import out.entity.PlayerEntity;

public class PlayerMapper {

    public PlayerEntity toEntity(String nickName) {
        PlayerEntity entity = new PlayerEntity();
        entity.setNickName(nickName);
        return entity;
    }

    public Player toDomain(PlayerEntity entity) {
        return new Player(entity.getId(), entity.getNickName());
    }
}
