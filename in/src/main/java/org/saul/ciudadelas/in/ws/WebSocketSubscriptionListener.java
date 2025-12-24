package org.saul.ciudadelas.in.ws;

import org.saul.ciudadelas.domain.GameEvent;
import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.players.Player;
import org.saul.ciudadelas.in.dto.PlayerPrivateInfoDTO;
import org.saul.ciudadelas.in.mappers.PlayerPrivateMapper;
import org.saul.ciudadelas.services.GameService;
import org.saul.ciudadelas.services.LobbyService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Component
public class WebSocketSubscriptionListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final LobbyService lobbyService;
    private final GameService gameService;
    private final WebSocketSender webSocketSender;

    public WebSocketSubscriptionListener(
            SimpMessagingTemplate messagingTemplate,
            LobbyService lobbyService,
            GameService gameService,
            WebSocketSender webSocketSender
    ) {
        this.messagingTemplate = messagingTemplate;
        this.lobbyService = lobbyService;
        this.gameService = gameService;
        this.webSocketSender = webSocketSender;
    }

    @EventListener
    public void handleSubscribe(SessionSubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String destination = accessor.getDestination();
        Principal user = accessor.getUser();

        if (destination == null) return;


        if (destination.startsWith("/topic/players/")) {
            handlePlayersSubscribe(destination);
        }


        if (destination.startsWith("/topic/game/")) {
            handleGameSubscribe(destination);
        }


        if (destination.startsWith("/user/queue/game/")) {
            handlePrivateGameSubscribe(destination, user);
        }
    }

    private void handlePlayersSubscribe(String destination) {
        try {
            UUID lobbyId = UUID.fromString(destination.replace("/topic/players/", ""));
            List<String> players = lobbyService.getAllPlayers(lobbyId);

            messagingTemplate.convertAndSend(
                    "/topic/players/" + lobbyId,
                    players
            );
        } catch (IllegalArgumentException ignored) {}
    }

    private void handleGameSubscribe(String destination) {
        try {
            UUID gameId = UUID.fromString(destination.replace("/topic/game/", ""));

            GameEvent currentState = gameService.getCurrentGameEvent(gameId);
            System.out.println("GameEvent: "+currentState);
            webSocketSender.publishEvent(currentState);

        } catch (IllegalArgumentException ignored) {}
    }

    private void handlePrivateGameSubscribe(String destination, Principal user) {
        if (user == null) return;

        try {
            UUID gameId = UUID.fromString(
                    destination.replace("/user/queue/game/", "")
            );

            String nick = user.getName();

            Game game = gameService.getGame(gameId);
            if (game == null) return;

            Player player = game.getPlayers().stream()
                    .filter(p -> p.getNickName().equals(nick))
                    .findFirst()
                    .orElse(null);

            System.out.println("PlayerPrivate: "+ player );

            if (player == null) return;

            PlayerPrivateInfoDTO dto =
                    PlayerPrivateMapper.toPlayerPrivateInfoDTO(player);

            messagingTemplate.convertAndSendToUser(
                    nick,
                    "/queue/game/" + gameId,
                    dto
            );
        } catch (IllegalArgumentException ignored) {}
    }


}
