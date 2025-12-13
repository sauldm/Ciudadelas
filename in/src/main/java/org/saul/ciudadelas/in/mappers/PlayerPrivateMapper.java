package org.saul.ciudadelas.in.mappers;

import org.saul.ciudadelas.in.dto.PlayerPrivateInfoDTO;
import org.saul.ciudadelas.domain.game.players.Player;

public class PlayerPrivateMapper {

    public static PlayerPrivateInfoDTO toPlayerPrivateInfoDTO(Player player){
        PlayerPrivateInfoDTO playerPrivateInfoDTO = new PlayerPrivateInfoDTO();
        playerPrivateInfoDTO.setDistrictsInHand(
                CardDTOMapper.toCardDTOList(player.getDistrictDeckCardsInHand().getCards())
        );
        return playerPrivateInfoDTO;
    }
}
