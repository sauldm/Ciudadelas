package org.saul.ciudadelas.out.mapper;

import org.saul.ciudadelas.domain.game.players.Player;
import org.saul.ciudadelas.out.entity.PlayerEntity;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    public PlayerEntity toEntity(Player player) {
        PlayerEntity entity = new PlayerEntity();
        if (player.getId() != null) entity.setId(player.getId());
        entity.setNickName(player.getNickName());
        entity.setWins(player.getWins());
        return entity;
    }

    public Player toDomain(PlayerEntity entity) {
        return new Player(entity.getId(), entity.getNickName(), entity.getWins());
    }
}
