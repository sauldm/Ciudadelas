package in;

import org.saul.ciudadelas.domain.lobby.Lobby;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import services.GameService;
import services.LobbyService;
import services.PlayerService;

import java.util.UUID;

@Controller
public class GameController {

    private GameService gameService;
    private LobbyService lobbyService;
    private PlayerService playerService;

    private SimpMessagingTemplate simpMessagingTemplate;


    public GameController(GameService gameService, LobbyService lobbyService, PlayerService playerService, SimpMessagingTemplate simpMessagingTemplate) {
        this.gameService = gameService;
        this.lobbyService = lobbyService;
        this.playerService = playerService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/player/{id}/new")
    public void registerNewPlayer(String name) {
        playerService.createNewPlayer(name);
    }

    @MessageMapping("/lobby/{id}/new")
    public void startNewLobby(String id) {
        Lobby lobby = lobbyService.createNewLobby(UUID.fromString(id));

    }


}
