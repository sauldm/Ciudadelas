package in;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import services.GameService;
import services.LobbyService;

@Controller
public class GameController {

    private GameService gameService;
    private LobbyService lobbyService;

    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/lobby/{id}/new")
    public void startNewLobby(Long id) {
        var lobby = lobbyService.getLobby(id);

    }


}
