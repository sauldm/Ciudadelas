package in.mappers;

import in.dto.PlayerCommonInfoDTO;
import in.dto.PlayerPrivateInfoDTO;
import org.saul.ciudadelas.domain.game.players.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerPrivateMapper {

    public static PlayerPrivateInfoDTO toPlayerPrivateInfoDTO(Player player){
        PlayerPrivateInfoDTO playerPrivateInfoDTO = new PlayerPrivateInfoDTO();
        playerPrivateInfoDTO.setDistrictsInHand(
                CardDTOMapper.toCardDTOList(player.getDistrictDeckCardsInHand().getCards())
        );
        return playerPrivateInfoDTO;
    }
}
