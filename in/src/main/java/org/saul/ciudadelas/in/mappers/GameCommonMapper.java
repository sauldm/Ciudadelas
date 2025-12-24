package org.saul.ciudadelas.in.mappers;

import org.saul.ciudadelas.in.dto.GameCommonInfoDTO;
import org.saul.ciudadelas.domain.game.Game;

public class GameCommonMapper {

    public static GameCommonInfoDTO toGameCommonInfoDTO(Game game){
        GameCommonInfoDTO gameCommonInfoDTO = new GameCommonInfoDTO();
        gameCommonInfoDTO.setId(game.getId());
        gameCommonInfoDTO.setCharacterTurnId(game.getCharacterId());
        gameCommonInfoDTO.setPlayerCommonInfoDTOS(PlayersCommonMapper.toPlayerCommonInfoDTOList(game.getPlayers()));
        gameCommonInfoDTO.setCharacterRobbed(game.getCharacterRobbed());
        gameCommonInfoDTO.setCharacterSkipped(game.getCharacterSkipped());
        gameCommonInfoDTO.setTurnCompleted(game.getActualTurn().isTurnCompleted());
        gameCommonInfoDTO.setCharacterHabilityUsed(game.getActualTurn().isCharacterHabilityUsed());
        gameCommonInfoDTO.setDistrictsHabilityUsed(game.getActualTurn().getDistrictsHabilityUsedThisRound());
        return gameCommonInfoDTO;
    }
}
