package boot;

import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.actions.*;
import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;
import org.saul.ciudadelas.domain.game.players.Player;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        ThiefActionCard a = new ThiefActionCard(2L);
        AssassinActionCard b = new AssassinActionCard(1L);
        KingActionCard k = new KingActionCard(5L);


        DeckCards deckCharacterCards2 = new DeckCards();
        deckCharacterCards2.addCards(List.of(
                k,
                b,
                a

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
                new Player(1L,"Saul"),
                new Player(2L,"DDD")

        );
        Game game = Game.initializeNewGame(deckCards,players,deckCharacterCards2);

        System.out.println(players);

        game.addRound();

        System.out.println(players);
        System.out.println(game.rounds);


        a.execute(game,k);

        System.out.println(players);

    }
}
