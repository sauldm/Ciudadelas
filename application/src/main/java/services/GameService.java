package services;

import org.saul.ciudadelas.domain.game.Events;
import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;
import org.saul.ciudadelas.domain.game.deck_cards.cards.DistrictCard;
import org.saul.ciudadelas.domain.game.players.Player;
import org.springframework.stereotype.Service;
import org.saul.ciudadelas.ports.CardRepositoryPort;
import org.saul.ciudadelas.ports.PlayerRepositoryPort;

import java.util.List;

@Service
public class GameService {
    private final CardRepositoryPort cardRepositoryPort;
    private final PlayerRepositoryPort playerRepositoryPort;
    private Game game;
    private List<Events> gameEvents;

    public GameService(CardRepositoryPort cardRepositoryPort, PlayerRepositoryPort playerRepositoryPort) {
        this.cardRepositoryPort = cardRepositoryPort;
        this.playerRepositoryPort = playerRepositoryPort;
    }

    public Game startGame(Long id) {
        DeckCards<DistrictCard> allDistrictCards = cardRepositoryPort.findAllCards();
        List<Player> players = playerRepositoryPort.findAllPlayers();
        this.game = Game.initializeNewGame(id,allDistrictCards, players);
        processGameEvents();
        return game;
    }

    public Long getCharacterTurn(){
        return game.getActualRound().getActualTurn().getCharacterId();
    }

    public Long nextStep(){
        game.nextStep();
        processGameEvents();
        return game.getActualRound().getActualTurn().getCharacterId();
    }

    public void executePlayerCharacterAbility(Long characterCardActionId, Long targetId) {
        game.executePlayerCharacterAbility(characterCardActionId,targetId);
        processGameEvents();

    }

    public void executePlayerDistrictAbility(Long districtCardActionId) {
        game.executeDistrictAbility(districtCardActionId);
        processGameEvents();

    }

    public void buildDistrict(Long districtCardId, Long characterCardId) {
        game.buildDistrictCard(districtCardId, characterCardId);
        processGameEvents();


    }

    public void playerChooseCoins(){
        game.characterChooseCoins();
        processGameEvents();

    }

    public void playerChooseCards(){
        game.characterChooseCards();
        processGameEvents();

    }

    private void processGameEvents() {
        this.gameEvents = game.getEventsBuffer();
    }
}
