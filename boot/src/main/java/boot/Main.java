package boot;

import org.saul.ciudadelas.domain.game.deck_cards.cards.Card;
import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;
import org.saul.ciudadelas.domain.game.deck_cards.actions.TakeThreeActionCard;
import org.saul.ciudadelas.domain.game.players.Player;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        DeckCards deckCards = new DeckCards();
        deckCards.addCards(List.of(
                new TakeThreeActionCard(),
                new TakeThreeActionCard(),
                new TakeThreeActionCard(),
                new TakeThreeActionCard(),
                new TakeThreeActionCard()


        ));
        Player player = new Player();
        player.addGold(3);

        Card card = new TakeThreeActionCard();



        /*Game game = new Game(deckCards);

        if (card instanceof TakeThreeActionCard card1){
            card1.execute(game,player);
        }

        System.out.println(player);
        System.out.println(deckCards);*/
    }
}
