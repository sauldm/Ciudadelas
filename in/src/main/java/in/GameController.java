package in;

import in.dto.*;
import in.mappers.PlayerMapper;
import in.ws.WebSocketSender;
import jakarta.websocket.server.PathParam;
import org.saul.ciudadelas.domain.GameEvent;
import org.saul.ciudadelas.domain.game.players.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import services.GameService;
import services.LobbyService;
import services.PlayerService;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static in.Constants.*;


@Controller
public class GameController {

    private GameService gameService;
    private LobbyService lobbyService;
    private PlayerService playerService;
    private WebSocketSender webSocketSender;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    public GameController(GameService gameService, LobbyService lobbyService, PlayerService playerService, WebSocketSender webSocketSender) {
        this.gameService = gameService;
        this.lobbyService = lobbyService;
        this.playerService = playerService;
        this.webSocketSender = webSocketSender;
    }

    @MessageMapping("/hello")
    public void debug(@Payload String payload) {
        System.out.println("Payload RAW: " + payload);
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) {
        System.out.println("Nombre recibido: " + message.getName());
        return new Greeting("Hello, " + message.getName() + "!");
    }

    @MessageMapping("/mensaje")
    @SendTo("/topic/mensajes")
    public String recibirMensaje(String mensaje) {
        System.out.println("Mensaje recibido: " + mensaje);
        return "Servidor recibió: " + mensaje;
    }

    @PostMapping("/startLobby/{id}")
    public boolean startNewLobby(@PathParam("id") UUID id) {
        return lobbyService.createNewLobby(id);
    }

    @MessageMapping("/joinLobby")
    @SendTo("/topic")
    public void joinLobby(LobbyAddPlayer lobbyAddPlayer) {
        Player player = playerService.getOrCreatePlayer(lobbyAddPlayer.getNickName());
        lobbyService.addPlayerToLobby(lobbyAddPlayer.getId(), player.getId());
    }



    @MessageMapping("/createGame")
    public void startNewGame(LobbyDTO lobbyDTO,Principal principal){
        if (lobbyDTO == null) throw new RuntimeException("El lobby no puede ser null");
        if (lobbyDTO.getPlayers() == null) throw new RuntimeException("Los players no pueden ser null");
        if (lobbyDTO.getPlayers().size() !=  PLAYERS_PER_GAME) throw new RuntimeException("Los jugadores deben ser los descritos");
        List<Player> players = lobbyDTO.getPlayers()
                .stream()
                .map(PlayerMapper::toDomain)
                .toList();
        GameEvent gameEvent = gameService.startGame(lobbyDTO.getId(),players);
        webSocketSender.publishEvent(gameEvent);
        webSocketSender.sendPrivateInfo(gameEvent,principal);
        lobbyService.deleteLobby(lobbyDTO.getId());
    }

    @MessageMapping("/deleteGame/{id}")
    public void deleteGame(String gameId, Principal principal){
        GameEvent gameEvent = gameService.deleteGame(UUID.fromString(gameId));
        webSocketSender.publishEvent(gameEvent);
        webSocketSender.sendPrivateInfo(gameEvent,principal);

    }

    @MessageMapping("/getTurn")
    public void getCharacterTurn(String gameId){
        UUID id = UUID.fromString(gameId);
        Long idCharacter = gameService.getCharacterTurn(id);
        webSocketSender.publishId(id,idCharacter);
    }

    @MessageMapping("/nextStep")
    public void nextStep(String gameId, Principal principal){
        GameEvent gameEvent = gameService.nextStep(UUID.fromString(gameId));
        webSocketSender.publishEvent(gameEvent);
        webSocketSender.sendPrivateInfo(gameEvent,principal);
    }

    @MessageMapping("/CharacterHability")
    public void executePlayerHability(ExecuteCharacterHabilityDTO executeCharacterHabilityDTO, Principal principal){
        if (executeCharacterHabilityDTO.getGameId() == null) throw new RuntimeException("El id del game no puede ser nulo");
        if (executeCharacterHabilityDTO.getCharacterId() == null) throw new RuntimeException("El id del character no puede ser nulo");
        if (executeCharacterHabilityDTO.getTargetId() == null) throw new RuntimeException("El id del target no puede ser nulo");

        UUID gameId = UUID.fromString(executeCharacterHabilityDTO.getGameId());

        GameEvent gameEvent = gameService.executePlayerCharacterAbility(
                gameId,
                executeCharacterHabilityDTO.getCharacterId(),
                executeCharacterHabilityDTO.getTargetId()
        );
        webSocketSender.publishEvent(gameEvent);
        webSocketSender.sendPrivateInfo(gameEvent,principal);
    }

    @MessageMapping("/DistrictHability")
    public void executeDistrictHability(ExecuteDistrictHabilityDTO executeDistrictHabilityDTO, Principal principal){
        if (executeDistrictHabilityDTO.getGameId() == null) throw new RuntimeException("El id del game no puede ser nulo");
        if (executeDistrictHabilityDTO.getDistrictId() == null) throw new RuntimeException("El id del district no puede ser nulo");

        UUID gameId = UUID.fromString(executeDistrictHabilityDTO.getGameId());

        GameEvent gameEvent = gameService.executePlayerDistrictAbility(
                gameId,
                executeDistrictHabilityDTO.getDistrictId()
        );

        webSocketSender.publishEvent(gameEvent);
        webSocketSender.sendPrivateInfo(gameEvent,principal);
    }

    @MessageMapping("/build")
    public void buildDistrict(BuildDistrictDTO buildDistrictDTO, Principal principal){
        if (buildDistrictDTO.getGameId() == null) throw new RuntimeException("El id del game no puede ser nulo");
        if (buildDistrictDTO.getDistrictId() == null) throw new RuntimeException("El id del district no puede ser nulo");
        if (buildDistrictDTO.getCharacterId() == null) throw new RuntimeException("El id del character no puede ser nulo");

        UUID gameId = UUID.fromString(buildDistrictDTO.getGameId());
        GameEvent gameEvent = gameService.buildDistrict(
                gameId,
                buildDistrictDTO.getDistrictId(),
                buildDistrictDTO.getCharacterId()
        );

        webSocketSender.publishEvent(gameEvent);
        webSocketSender.sendPrivateInfo(gameEvent,principal);
    }

    @MessageMapping("/chooseCoin")
    public void chooseCoin(String gameId, Principal principal){
        if (gameId == null) throw new RuntimeException("El id del game no puede ser nulo");

        UUID id = UUID.fromString(gameId);
        GameEvent gameEvent = gameService.playerChooseCoins(id);

        webSocketSender.publishEvent(gameEvent);
        webSocketSender.sendPrivateInfo(gameEvent,principal);
    }

    @MessageMapping("/chooseCard")
    public void chooseCards(String gameId, Principal principal){
        if (gameId == null) throw new RuntimeException("El id del game no puede ser nulo");

        UUID id = UUID.fromString(gameId);
        GameEvent gameEvent = gameService.playerChooseCards(id);

        webSocketSender.publishEvent(gameEvent);
        webSocketSender.sendPrivateInfo(gameEvent,principal);
    }
}
