package services;

import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;
import org.saul.ciudadelas.domain.game.deck_cards.cards.DistrictCard;
import org.saul.ciudadelas.domain.game.players.Player;
import org.springframework.stereotype.Service;
import org.saul.ciudadelas.ports.CardRepositoryPort;
import org.saul.ciudadelas.ports.PlayerRepositoryPort;

import java.util.List;
import java.util.Map;

@Service
public class GameService {
    private final CardRepositoryPort cardRepositoryPort;
    private final PlayerRepositoryPort playerRepositoryPort;
    private Map<Long,Game> games;

    public GameService(CardRepositoryPort cardRepositoryPort, PlayerRepositoryPort playerRepositoryPort) {
        this.cardRepositoryPort = cardRepositoryPort;
        this.playerRepositoryPort = playerRepositoryPort;
    }



    public Game startGame(Long id, List<Player> players) {
        DeckCards<DistrictCard> allDistrictCards = cardRepositoryPort.findAllCards();
        Game game =  Game.initializeNewGame(id,allDistrictCards, players);
        games.put(id, game);
        return game;
    }

    public Game getGame(Long id) {
        return games.get(id);
    }

    public void deleteGame(Long id) {
        games.remove(id);
    }

    public Long getCharacterTurn(Long id){
        return getGame(id).getActualRound().getActualTurn().getCharacterId();
    }

    public Long nextStep(Long id){
        Game game = getGame(id);
        game.nextStep();
        return game.getActualRound().getActualTurn().getCharacterId();
    }

    public void executePlayerCharacterAbility(Long id,Long characterCardActionId, Long targetId) {
        getGame(id).executePlayerCharacterAbility(characterCardActionId,targetId);

    }

    public void executePlayerDistrictAbility(Long id,Long districtCardActionId) {
        getGame(id).executeDistrictAbility(districtCardActionId);
    }

    public void buildDistrict(Long id,Long districtCardId, Long characterCardId) {
        getGame(id).buildDistrictCard(districtCardId, characterCardId);
    }

    public void playerChooseCoins(Long id){
        getGame(id).characterChooseCoins();

    }

    public void playerChooseCards(Long id){
        getGame(id).characterChooseCards();
    }
}
