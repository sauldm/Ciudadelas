package org.saul.ciudadelas.services;

import org.saul.ciudadelas.domain.GameEvent;
import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;
import org.saul.ciudadelas.domain.game.deck_cards.cards.DistrictCard;
import org.saul.ciudadelas.domain.game.players.Player;
import org.saul.ciudadelas.ports.PlayerRepositoryPort;
import org.springframework.stereotype.Service;
import org.saul.ciudadelas.ports.CardRepositoryPort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class GameService {
    private final CardRepositoryPort cardRepositoryPort;
    private Map<UUID,Game> games = new HashMap<>();
    private GameEvent eventBuffer;

    private PlayerRepositoryPort playerRepositoryPort;

    public GameService(CardRepositoryPort cardRepositoryPort, PlayerRepositoryPort playerRepositoryPort) {
        this.cardRepositoryPort = cardRepositoryPort;
        this.playerRepositoryPort = playerRepositoryPort;
    }

    public Game startGame(UUID id, List<Player> players) {
        List<DistrictCard> districtCards = cardRepositoryPort.findAllCards();
        DeckCards<DistrictCard> allDistrictCards = new DeckCards<>();
        allDistrictCards.addCards(districtCards);
        Game game = Game.initializeNewGame(id,allDistrictCards, players);
        games.put(id, game);
        return game;
    }

    public Game getGame(UUID id) {
        Game game = games.get(id);
        if (game == null) {
            throw new IllegalStateException("Game not found: " + id);
        }
        return game;
    }
    public GameEvent getCurrentGameEvent(UUID id) {
        Game game = games.get(id);
        if (game == null) return null;
        return game.clearEventsBuffer();
    }



    public GameEvent deleteGame(UUID id) {
        Game game = getGame(id);
        games.remove(id);
        eventBuffer = game.clearEventsBuffer();
        return eventBuffer;

    }

    public GameEvent nextStep(UUID id){
        Game game = getGame(id);
        game.nextStep();
        eventBuffer = game.clearEventsBuffer();
        if (eventBuffer.getEvents().getLast().getType().toString().equals("GAME_ENDED")){
            game.getPlayers()
                    .forEach(playerRepositoryPort::save);
        }
        return eventBuffer;

    }

    public GameEvent executePlayerCharacterAbility(UUID id,Long characterCardActionId, Long targetId) {
        Game game = getGame(id);
        game.executePlayerCharacterAbility(characterCardActionId,targetId);
        eventBuffer = game.clearEventsBuffer();
        return eventBuffer;

    }

    public GameEvent executePlayerDistrictAbility(UUID id,Long districtCardActionId) {
        Game game = getGame(id);

        game.executeDistrictAbility(districtCardActionId);
        eventBuffer = game.clearEventsBuffer();
        return eventBuffer;
    }

    public GameEvent buildDistrict(UUID id,Long districtCardId, Long characterCardId) {
        Game game = getGame(id);

        game.buildDistrictCard(districtCardId, characterCardId);
        eventBuffer = game.clearEventsBuffer();
        return eventBuffer;
    }

    public GameEvent playerChooseCoins(UUID id){
        Game game = getGame(id);

        game.characterChooseCoins();
        eventBuffer = game.clearEventsBuffer();
        return eventBuffer;

    }

    public GameEvent playerChooseCards(UUID id){
        Game game = getGame(id);

        game.characterChooseCards();
        eventBuffer = game.clearEventsBuffer();
        return eventBuffer;
    }

    public boolean existsGame(UUID id) {
        return games.containsKey(id);
    }

    public Player getPlayer(UUID gameId, String nickName) {
        Game game = games.get(gameId);
        if (game == null) return null;

        return game.getPlayers()
                .stream()
                .filter(player -> player.getNickName().equals(nickName))
                .findFirst()
                .orElse(null);
    }
}
