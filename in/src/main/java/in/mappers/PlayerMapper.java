package in.mappers;

import in.dto.PlayerDTO;
import org.saul.ciudadelas.domain.game.players.Player;

public class PlayerMapper {

        public static Player toDomain(PlayerDTO dto) {
            return new Player(dto.getId(),dto.getNickName());
        }
}
