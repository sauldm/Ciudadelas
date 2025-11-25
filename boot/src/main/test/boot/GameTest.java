package boot;

import org.junit.Test;
import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;
import org.saul.ciudadelas.domain.game.deck_cards.actions.*;
import org.saul.ciudadelas.domain.game.deck_cards.cards.DistrictCard;
import org.saul.ciudadelas.domain.game.players.Player;

import java.util.ArrayList;
import java.util.List;

public class GameTest {

    @Test
    public void gameTest() {
        AssassinActionCard a = new AssassinActionCard();
        ThiefActionCard b = new ThiefActionCard();
        WizardActionCard c = new WizardActionCard();
        KingActionCard d = new KingActionCard();
        BishopActionCard e = new BishopActionCard();
        MerchantActionCard f = new MerchantActionCard();
        ArchitectActionCard g = new ArchitectActionCard();
        MilitaryActionCard h = new MilitaryActionCard();


        DeckCards deckCharacterCards2 = new DeckCards();
        deckCharacterCards2.addCards(List.of(
                a,b,c,d

        ));

        List<DistrictCard> k = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            k.add(new TakeThreeActionCard(((long) i)));
        }

        DeckCards deckDistrictCards = new DeckCards();
        deckDistrictCards.addCards(k);


        List<Player> players = List.of(
                new Player(1L, "Saul"),
                new Player(2L, "DDD")

        );
        Game game = Game.initializeNewGame(deckDistrictCards, players, deckCharacterCards2);



    }

}
