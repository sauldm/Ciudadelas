package boot;

import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.actions.AssassinActionCard;
import org.saul.ciudadelas.domain.game.deck_cards.actions.KingActionCard;
import org.saul.ciudadelas.domain.game.deck_cards.cards.Card;
import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;
import org.saul.ciudadelas.domain.game.deck_cards.actions.TakeThreeActionCard;
import org.saul.ciudadelas.domain.game.players.Player;

import java.util.List;

public class Main {
    public static void main(String[] args) {


        DeckCards deckCharacterCards1 = new DeckCards();
        deckCharacterCards1.addCards(List.of(
                new AssassinActionCard()
        ));

        DeckCards deckCharacterCards2 = new DeckCards();
        deckCharacterCards2.addCards(List.of(
                new KingActionCard(),
                new AssassinActionCard()
        ));

        DeckCards deckCharacterCards3 = new DeckCards();
        deckCharacterCards3.addCards(List.of(
                new AssassinActionCard()
        ));

        DeckCards deckCards = new DeckCards();
        deckCards.addCards(List.of(
                new TakeThreeActionCard(),
                new TakeThreeActionCard(),
                new TakeThreeActionCard(),
                new TakeThreeActionCard(),
                new TakeThreeActionCard(),
                new TakeThreeActionCard(),
                new TakeThreeActionCard(),
                new TakeThreeActionCard()




        ));


        List<Player> players = List.of(
                new Player(1L,"Saul",deckCharacterCards1),
                new Player(2L,"DDD",deckCharacterCards2),
                new Player(3L,"WWW",deckCharacterCards3)


        );
        Game game = new Game(deckCards,players,deckCharacterCards2);

        System.out.println(game.playerChooseOrder);

        game.getCharacterOrder();

        System.out.println(game.playerChooseOrder);
    }
}
