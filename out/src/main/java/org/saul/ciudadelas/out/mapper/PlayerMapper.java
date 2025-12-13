package org.saul.ciudadelas.out.mapper;

import org.saul.ciudadelas.domain.game.players.Player;
import org.saul.ciudadelas.out.entity.PlayerEntity;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    public PlayerEntity toEntity(Long id,String nickName) {
        PlayerEntity entity = new PlayerEntity();
        if (id != null) entity.setId(id);
        entity.setNickName(nickName);
        return entity;
    }

    public Player toDomain(PlayerEntity entity) {
        return new Player(entity.getId(), entity.getNickName());
    }
}
