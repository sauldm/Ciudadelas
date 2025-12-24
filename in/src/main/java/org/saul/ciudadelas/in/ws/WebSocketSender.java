package org.saul.ciudadelas.in.ws;


import org.saul.ciudadelas.in.dto.EventsMessagesDTO;
import org.saul.ciudadelas.in.dto.GameCommonInfoDTO;
import org.saul.ciudadelas.in.dto.GameEventDTO;
import org.saul.ciudadelas.in.dto.PlayerPrivateInfoDTO;
import org.saul.ciudadelas.in.mappers.GameCommonMapper;
import org.saul.ciudadelas.in.mappers.GameEventDTOMapper;
import org.saul.ciudadelas.in.mappers.PlayerPrivateMapper;
import org.saul.ciudadelas.domain.GameEvent;
import org.saul.ciudadelas.domain.game.players.Player;
import org.saul.ciudadelas.ports.EventsInPort;

import org.saul.ciudadelas.services.GameService;
import org.saul.ciudadelas.services.LobbyService;
import org.saul.ciudadelas.services.PlayerService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
public class WebSocketSender implements EventsInPort {

    private final SimpMessagingTemplate messagingTemplate;
    private final LobbyService lobbyService;
    private final PlayerService playerService;

    private final GameService gameService;

    public WebSocketSender(SimpMessagingTemplate simpMessagingTemplate, LobbyService lobbyService, PlayerService playerService, GameService gameService) {
        this.messagingTemplate = simpMessagingTemplate;
        this.lobbyService = lobbyService;
        this.playerService = playerService;
        this.gameService = gameService;
    }

    @Override
    public void publishEvent(GameEvent gameEvent) {
        GameEventDTO gameEventDTO = GameEventDTOMapper.toGameEventDTO(gameEvent);
        messagingTemplate.convertAndSend("/topic/game/" + gameEvent.getGame().getId(), gameEventDTO);
    }

    public void publishLobies() {
        messagingTemplate.convertAndSend("/topic/lobbies",lobbyService.getLobbiesWithLessThan2Players());
    }

    public void sendPrivateInfo(GameEvent gameEvent) {
        for (Player player : gameEvent.getGame().getPlayers()) {
            PlayerPrivateInfoDTO playerPrivateInfoDTO = PlayerPrivateMapper.toPlayerPrivateInfoDTO(player);

            messagingTemplate.convertAndSendToUser(
                    player.getNickName(),
                    "/queue/game/" + gameEvent.getGame().getId(),
                    playerPrivateInfoDTO
            );
        }
    }


    public void publishPlayersLobby(UUID lobbyId, List<String> nickNames) {
        messagingTemplate.convertAndSend("/topic/players/" + lobbyId, nickNames);
    }

    public void sendGameStarted(UUID id) {
        messagingTemplate.convertAndSend(
                "/topic/gameCreated/" + id,
                id
        );
    }

    public void sendToPlayer(UUID id, Principal principal, Player player){
        messagingTemplate.convertAndSendToUser(
                principal.getName(),
                "/queue/game/" + id,
                PlayerPrivateMapper.toPlayerPrivateInfoDTO(player)
        );
    }
}
