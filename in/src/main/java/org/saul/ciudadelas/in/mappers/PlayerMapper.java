package org.saul.ciudadelas.in.mappers;

import org.saul.ciudadelas.in.dto.PlayerDTO;
import org.saul.ciudadelas.domain.game.players.Player;

public class PlayerMapper {

        public static Player toDomain(PlayerDTO dto) {
            return new Player(dto.getId(),dto.getNickName());
        }

        public static PlayerDTO toDTO(Player player){
            PlayerDTO playerDTO = new PlayerDTO();
            playerDTO.setId(player.getId());
            playerDTO.setNickName(player.getNickName());
            return playerDTO;
        }
}
