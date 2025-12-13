package org.saul.ciudadelas.services;

import org.saul.ciudadelas.domain.GameEvent;
import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;
import org.saul.ciudadelas.domain.game.deck_cards.cards.DistrictCard;
import org.saul.ciudadelas.domain.game.players.Player;
import org.springframework.stereotype.Service;
import org.saul.ciudadelas.ports.CardRepositoryPort;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class GameService {
    private final CardRepositoryPort cardRepositoryPort;
    private Map<UUID,Game> games;
    private GameEvent eventBuffer;

    public GameService(CardRepositoryPort cardRepositoryPort) {
        this.cardRepositoryPort = cardRepositoryPort;
    }



    public GameEvent startGame(UUID id, List<Player> players) {
        List<DistrictCard> districtCards = cardRepositoryPort.findAllCards();
        DeckCards<DistrictCard> allDistrictCards = new DeckCards<>();
        allDistrictCards.addCards(districtCards);
        Game game = Game.initializeNewGame(id,allDistrictCards, players);
        games.put(id, game);
        eventBuffer = getGame(id).clearEventsBuffer();
        return eventBuffer;
    }

    private Game getGame(UUID id) {
        return games.get(id);
    }

    public GameEvent deleteGame(UUID id) {
        Game game = getGame(id);
        games.remove(id);
        eventBuffer = game.clearEventsBuffer();
        return eventBuffer;

    }

    public Long getCharacterTurn(UUID id){
        return getGame(id).getActualRound().getActualTurn().getCharacterId();
    }

    public GameEvent nextStep(UUID id){
        Game game = getGame(id);
        game.nextStep();
        eventBuffer = game.clearEventsBuffer();
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
}
