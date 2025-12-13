package org.saul.ciudadelas.in;

import org.saul.ciudadelas.in.dto.*;
import org.saul.ciudadelas.in.mappers.LobbyDTOMapper;
import org.saul.ciudadelas.in.mappers.PlayerMapper;
import org.saul.ciudadelas.in.ws.WebSocketSender;
import org.saul.ciudadelas.domain.GameEvent;
import org.saul.ciudadelas.domain.game.players.Player;
import org.saul.ciudadelas.services.GameService;
import org.saul.ciudadelas.services.LobbyService;
import org.saul.ciudadelas.services.PlayerService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static org.saul.ciudadelas.in.Constants.*;


@RestController
public class GameController {

    private GameService gameService;
    private LobbyService lobbyService;
    private PlayerService playerService;
    private WebSocketSender webSocketSender;


    public GameController(GameService gameService, LobbyService lobbyService, PlayerService playerService, WebSocketSender webSocketSender) {
        this.gameService = gameService;
        this.lobbyService = lobbyService;
        this.playerService = playerService;
        this.webSocketSender = webSocketSender;
    }


    @MessageMapping("/mensaje")
    public void recibirMensaje(String mensaje, Principal principal) {
        System.out.println("Principal actual: " + principal.getName());
        webSocketSender.sendPrivate(mensaje, principal);
    }

    @MessageMapping("/getLobbies")
    public void getLobbies(){
        lobbyService.getLobbiesWithLessThan2Players();
        webSocketSender.publishLobies();

    }

    @MessageMapping("/getPlayers/{id}")
    public void getLobbyPlayers(@DestinationVariable UUID id){
        webSocketSender.publishPlayersLobby(id, lobbyService.getAllPlayers(id));
    }

    @PostMapping("/api/lobbies")
    public UUID createLobby() {
        UUID lobbyId = UUID.randomUUID();
        lobbyService.createNewLobby(lobbyId);

        webSocketSender.publishLobies();
        return lobbyId;
    }

    @MessageMapping("/joinLobby")
    public void joinLobby(@Payload LobbyAddPlayer dto, Principal principal) {
        String nick = principal.getName();
        System.out.println("join:" + nick);
        lobbyService.addPlayerToLobby(dto.getId(), nick);
        webSocketSender.publishLobies();
        webSocketSender.publishPlayersLobby(dto.getId(), lobbyService.getAllPlayers(dto.getId()));
    }


    @MessageMapping("/unjoinLobby")
    public void unjoinLobby(@Payload LobbyAddPlayer dto){
        lobbyService.removePlayerFromLobby(dto.getId(), dto.getNickName());
        webSocketSender.publishPlayersLobby(dto.getId(), lobbyService.getAllPlayers(dto.getId()));
        webSocketSender.publishLobies();
    }


    @MessageMapping("/createGame")
    public void startNewGame(LobbyDTO lobbyDTO, Principal principal){
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
