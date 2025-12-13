package org.saul.ciudadelas.in.mappers;

import org.saul.ciudadelas.in.dto.GameCommonInfoDTO;
import org.saul.ciudadelas.domain.game.Game;

public class GameCommonMapper {

    public static GameCommonInfoDTO toGameCommonInfoDTO(Game game){
        GameCommonInfoDTO gameCommonInfoDTO = new GameCommonInfoDTO();
        gameCommonInfoDTO.setPlayerCommonInfoDTOS(PlayersCommonMapper.toPlayerCommonInfoDTOList(game.getPlayers()));

        return gameCommonInfoDTO;
    }
}
