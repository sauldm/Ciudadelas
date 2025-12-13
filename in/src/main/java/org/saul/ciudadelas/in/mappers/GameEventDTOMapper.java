package org.saul.ciudadelas.in.mappers;

import org.saul.ciudadelas.in.dto.GameEventDTO;
import org.saul.ciudadelas.domain.GameEvent;

public class GameEventDTOMapper {

    public static GameEventDTO toGameEventDTO(GameEvent gameEvent){
        GameEventDTO gameEventDTO = new GameEventDTO();
        gameEventDTO.setGameCommonInfoDTO(GameCommonMapper.toGameCommonInfoDTO(gameEvent.getGame()));
        gameEventDTO.setEvents(gameEvent.getEvents().stream().map(Enum::name).toList());

        return gameEventDTO;
    }
}
