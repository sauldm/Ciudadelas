package org.saul.ciudadelas.in.mappers;

import org.saul.ciudadelas.in.dto.PlayerCommonInfoDTO;
import org.saul.ciudadelas.domain.game.players.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayersCommonMapper {

    public static PlayerCommonInfoDTO toPlayerCommonInfoDTO(Player player){
        PlayerCommonInfoDTO playerCommonInfoDTO = new PlayerCommonInfoDTO();
        playerCommonInfoDTO.setId(player.getId());
        playerCommonInfoDTO.setGold(player.getGold());
        playerCommonInfoDTO.setDistrictsBuilt(
                CardDTOMapper.toCardDTOList(player.getDistrictDeckCardsBuilt().getCards())
        );

        playerCommonInfoDTO.setCharacterCardsPlayed(
                CardDTOMapper.toCardDTOList(player.getCharacterCardsPlayed())
        );
        playerCommonInfoDTO.setNickName(player.getNickName());
        playerCommonInfoDTO.setNumberDistrictsInHand(player.getDistrictDeckCardsInHand().size());

        return playerCommonInfoDTO;
    }

    public static List<PlayerCommonInfoDTO> toPlayerCommonInfoDTOList(List<? extends Player> players) {
        List<PlayerCommonInfoDTO> dtos = new ArrayList<>();
        for (Player player : players) {
            dtos.add(toPlayerCommonInfoDTO(player));
        }
        return dtos;
    }
}
