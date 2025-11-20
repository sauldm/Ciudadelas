package boot;

import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.actions.*;
import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;
import org.saul.ciudadelas.domain.game.deck_cards.cards.DistrictCard;
import org.saul.ciudadelas.domain.game.players.Player;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

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
                a,
                b,
                c,
                d,
                e,
                f,
                g,
                h

        ));

        List<DistrictCard> k = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            k.add(new TwoPointsFinalActionCard(((long) i)));
        }

        DeckCards deckDistrictCards = new DeckCards();
        deckDistrictCards.addCards(k);


        List<Player> players = List.of(
                new Player(1L, "Saul"),
                new Player(2L, "DDD")

        );
        Game game = Game.initializeNewGame(deckDistrictCards, players, deckCharacterCards2);

        /*for (int i = 0; i < 8; i++) {
            System.out.println(i + "" + game.getActualRound().getActualTurn());
            game.buildDistrictCard(2L, game.getActualRound().getActualTurn().getCharacterId());
            System.out.println(i + "" + game.getActualRound().getActualTurn());

            if (game.getActualRound().getActualTurn().getCharacterId() == 8L){
                game.executePlayerCharacterAbility(game.getActualRound().getActualTurn().getCharacterId(), 1L);
            }else
            if (game.getActualRound().getActualTurn().getCharacterId() != 3L) {
                game.executePlayerCharacterAbility(game.getActualRound().getActualTurn().getCharacterId(), game.getActualRound().getActualTurn().getCharacterId()+2);
            } else {
                game.executePlayerCharacterAbility(game.getActualRound().getActualTurn().getCharacterId(), 0L);
            }
            game.getActualRound().getActualTurn().endTurn();
            game.nextStep();
        }*/

    }
}
