package in;

import in.dto.*;
import in.mappers.PlayerMapper;
import in.ws.WebSocketSender;
import org.saul.ciudadelas.domain.GameEvent;
import org.saul.ciudadelas.domain.game.players.Player;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import services.GameService;
import services.LobbyService;
import services.PlayerService;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class GameController {

    private GameService gameService;
    private LobbyService lobbyService;
    private PlayerService playerService;
    private final Map<String, Principal> connectedPlayers = new ConcurrentHashMap<>();
    private WebSocketSender webSocketSender;


    public GameController(GameService gameService, LobbyService lobbyService, PlayerService playerService, WebSocketSender webSocketSender) {
        this.gameService = gameService;
        this.lobbyService = lobbyService;
        this.playerService = playerService;
    }

    @MessageMapping("/createPlayer")
    @SendToUser("/queue/start")
    public boolean registerNewPlayer(PlayerDTO playerDTO) {
        return playerService.createNewPlayer(PlayerMapper.toDomain(playerDTO)) == null;
    }

    @MessageMapping("/joinGame")
    public void joinGame(Principal principal, PlayerDTO dto) {
        connectedPlayers.put(dto.getNickName(), principal);
    }

    @MessageMapping("/createLobby")
    @SendTo("/topic/lobby")
    public boolean startNewLobby(String id) {
        return lobbyService.createNewLobby(UUID.fromString(id));
    }

    @MessageMapping("/createGame")
    public void startNewGame(LobbyDTO lobbyDTO){
        List<Player> players = lobbyDTO.getPlayers()
                .stream()
                .map(PlayerMapper::toDomain)
                .toList();
        GameEvent gameEvent = gameService.startGame(lobbyDTO.getId(),players);
        webSocketSender.publishEvent(gameEvent);
        webSocketSender.sendPrivateInfo(gameEvent,connectedPlayers);
        lobbyService.deleteLobby(lobbyDTO.getId());
    }

    @MessageMapping("/deleteGame/{id}")
    public void deleteGame(String gameId){
        GameEvent gameEvent = gameService.deleteGame(UUID.fromString(gameId));
        webSocketSender.publishEvent(gameEvent);
        webSocketSender.sendPrivateInfo(gameEvent,connectedPlayers);

    }

    @MessageMapping("/getTurn")
    public void getCharacterTurn(String gameId){
        UUID id = UUID.fromString(gameId);
        Long idCharacter = gameService.getCharacterTurn(id);
        webSocketSender.publishId(id,idCharacter);
    }

    @MessageMapping("/nextStep")
    public void nextStep(String gameId){
        GameEvent gameEvent = gameService.nextStep(UUID.fromString(gameId));
        webSocketSender.publishEvent(gameEvent);
        webSocketSender.sendPrivateInfo(gameEvent,connectedPlayers);
    }

    @MessageMapping("/CharacterHability")
    public void executePlayerHability(ExecuteCharacterHabilityDTO executeCharacterHabilityDTO){
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
        webSocketSender.sendPrivateInfo(gameEvent,connectedPlayers);
    }

    @MessageMapping("/DistrictHability")
    public void executeDistrictHability(ExecuteDistrictHabilityDTO executeDistrictHabilityDTO){
        if (executeDistrictHabilityDTO.getGameId() == null) throw new RuntimeException("El id del game no puede ser nulo");
        if (executeDistrictHabilityDTO.getDistrictId() == null) throw new RuntimeException("El id del district no puede ser nulo");

        UUID gameId = UUID.fromString(executeDistrictHabilityDTO.getGameId());

        GameEvent gameEvent = gameService.executePlayerDistrictAbility(
                gameId,
                executeDistrictHabilityDTO.getDistrictId()
        );

        webSocketSender.publishEvent(gameEvent);
        webSocketSender.sendPrivateInfo(gameEvent,connectedPlayers);
    }

    public void buildDistrict(BuildDistrictDTO buildDistrictDTO){
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
        webSocketSender.sendPrivateInfo(gameEvent,connectedPlayers);
    }

    public void chooseCoin(String gameId){
        if (gameId == null) throw new RuntimeException("El id del game no puede ser nulo");

        UUID id = UUID.fromString(gameId);
        GameEvent gameEvent = gameService.playerChooseCoins(id);

        webSocketSender.publishEvent(gameEvent);
        webSocketSender.sendPrivateInfo(gameEvent,connectedPlayers);
    }

    public void chooseCards(String gameId){
        if (gameId == null) throw new RuntimeException("El id del game no puede ser nulo");

        UUID id = UUID.fromString(gameId);
        GameEvent gameEvent = gameService.playerChooseCards(id);

        webSocketSender.publishEvent(gameEvent);
        webSocketSender.sendPrivateInfo(gameEvent,connectedPlayers);
    }
}
