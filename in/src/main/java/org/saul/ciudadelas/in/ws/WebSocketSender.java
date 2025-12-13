package org.saul.ciudadelas.in.ws;


import org.saul.ciudadelas.in.dto.GameEventDTO;
import org.saul.ciudadelas.in.dto.LobbyDTO;
import org.saul.ciudadelas.in.dto.PlayerPrivateInfoDTO;
import org.saul.ciudadelas.in.mappers.GameEventDTOMapper;
import org.saul.ciudadelas.in.mappers.LobbyDTOMapper;
import org.saul.ciudadelas.in.mappers.PlayerPrivateMapper;
import org.saul.ciudadelas.domain.GameEvent;
import org.saul.ciudadelas.domain.game.players.Player;
import org.saul.ciudadelas.ports.EventsInPort;

import org.saul.ciudadelas.services.LobbyService;
import org.saul.ciudadelas.services.PlayerService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectEvent;


import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
public class WebSocketSender implements EventsInPort {

    private final SimpMessagingTemplate messagingTemplate;
    private final LobbyService lobbyService;
    private final PlayerService playerService;

    public WebSocketSender(SimpMessagingTemplate simpMessagingTemplate, LobbyService lobbyService, PlayerService playerService) {
        this.messagingTemplate = simpMessagingTemplate;
        this.lobbyService = lobbyService;
        this.playerService = playerService;
    }

    @Override
    public void publishEvent(GameEvent gameEvent) {
        GameEventDTO gameEventDTO = GameEventDTOMapper.toGameEventDTO(gameEvent);
        messagingTemplate.convertAndSend("/topic/game/" + gameEvent.getGame().getId(), gameEventDTO);
    }

    public void publishLobies() {
        messagingTemplate.convertAndSend("/topic/lobbies",lobbyService.getLobbiesWithLessThan2Players());
    }

    public void sendPrivateInfo(GameEvent gameEvent, Principal principal) {
        for (Player player : gameEvent.getGame().getPlayers()) {
            PlayerPrivateInfoDTO playerPrivateInfoDTO = PlayerPrivateMapper.toPlayerPrivateInfoDTO(player);
            if (principal != null) {
                messagingTemplate.convertAndSendToUser(
                        player.getNickName(),
                        "/queue/game/" + gameEvent.getGame().getId(),
                        playerPrivateInfoDTO
                );
            }
        }
    }

    public void sendPrivate(String msg, Principal principal) {
        messagingTemplate.convertAndSendToUser(
                principal.getName(),
                "/queue/game",
                msg
        );
    }

    public void publishId(UUID gameID, Long id) {
        messagingTemplate.convertAndSend("/topic/game/" + gameID, id);

    }

    public void publishPlayersLobby(UUID lobbyId, List<String> nickNames) {
        System.out.println("nombress  " +nickNames);
        messagingTemplate.convertAndSend("/topic/players/" + lobbyId, nickNames);
    }

}
