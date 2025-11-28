package services;

import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;
import org.saul.ciudadelas.domain.game.deck_cards.cards.DistrictCard;
import org.saul.ciudadelas.domain.game.players.Player;
import org.springframework.stereotype.Service;
import ports.CardRepositoryPort;
import ports.PlayerRepositoryPort;

import java.util.List;

@Service
public class GameService {
    private final CardRepositoryPort cardRepositoryPort;
    private final PlayerRepositoryPort playerRepositoryPort;
    private Game game;

    public GameService(CardRepositoryPort cardRepositoryPort, PlayerRepositoryPort playerRepositoryPort) {
        this.cardRepositoryPort = cardRepositoryPort;
        this.playerRepositoryPort = playerRepositoryPort;
    }

    public Game startGame() {
        DeckCards<DistrictCard> allDistrictCards = cardRepositoryPort.findAllCards();
        List<Player> players = playerRepositoryPort.findAllPlayers();
        this.game = Game.initializeNewGame(allDistrictCards, players);
        return game;
    }

    public void executePlayerCharacterAbility(Long characterCardActionId, Long targetId) {
        game.executePlayerCharacterAbility(characterCardActionId,targetId);
    }
}
