package in.ws;


import in.dto.GameEventDTO;
import in.dto.PlayerPrivateInfoDTO;
import in.mappers.GameEventDTOMapper;
import in.mappers.PlayerPrivateMapper;
import org.saul.ciudadelas.domain.GameEvent;
import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.players.Player;
import org.saul.ciudadelas.ports.EventsInPort;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;


public class WebSocketSender implements EventsInPort {

    private SimpMessagingTemplate messagingTemplate;

    public WebSocketSender(SimpMessagingTemplate simpMessagingTemplate){
        this.messagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void publishEvent(GameEvent gameEvent) {
        GameEventDTO gameEventDTO = GameEventDTOMapper.toGameEventDTO(gameEvent);
        messagingTemplate.convertAndSend("/topic/game/"+ gameEvent.getGame().getId(), gameEventDTO);
    }

    public void sendPrivateInfo(GameEvent gameEvent, Map<String, Principal> playersConnected) {
        for (Player player : gameEvent.getGame().getPlayers()) {
            Principal principal = playersConnected.get(player.getNickName());
            PlayerPrivateInfoDTO playerPrivateInfoDTO = PlayerPrivateMapper.toPlayerPrivateInfoDTO(player);
            if (principal != null) {
                messagingTemplate.convertAndSendToUser(
                        principal.getName(),
                        "/queue/juego/" + gameEvent.getGame().getId(),
                        playerPrivateInfoDTO
                );
            }
        }
    }

    public void publishId(UUID gameID,Long id){
        messagingTemplate.convertAndSend("/topic/game/"+ gameID, id);

    }
}
