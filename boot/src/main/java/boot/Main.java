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

        AssassinActionCard a = new AssassinActionCard(1L);
        ThiefActionCard b = new ThiefActionCard(2L);
        WizardActionCard c = new WizardActionCard(3L);
        KingActionCard d = new KingActionCard(4L);
        BishopActionCard e = new BishopActionCard(5L);
        MerchantActionCard f = new MerchantActionCard(6L);
        ArchitectActionCard g = new ArchitectActionCard(7L);
        MilitaryActionCard h = new MilitaryActionCard(8L);



        DeckCards deckCharacterCards2 = new DeckCards();
        deckCharacterCards2.addCards(List.of(
                b,
                c,
                d,
                e

        ));

        List<DistrictCard> k = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            k.add(new TwoPointsFinalActionCard( ((long) i)));
        }

        DeckCards deckDistrictCards = new DeckCards();
        deckDistrictCards.addCards(k);


        List<Player> players = List.of(
                new Player(1L,"Saul"),
                new Player(2L,"DDD")

        );
        Game game = Game.initializeNewGame(deckDistrictCards,players,deckCharacterCards2);

        game.executePlayerCharacterAbility(game.getActualRound().getActualTurn().getCharacter().getId(), c.getId());

        System.out.println(game.rounds);
        game.getActualRound().getActualTurn().endTurn();

        game.nextStep();

        game.executePlayerCharacterAbility(game.getActualRound().getActualTurn().getPlayer(), c.getId());


        System.out.println(game.rounds);
        game.getActualRound().getActualTurn().endTurn();


        game.nextStep();

        game.executePlayerCharacterAbility(game.getActualRound().getActualTurn().getPlayer(), c.getId());
        System.out.println(game.rounds);
        game.getActualRound().getActualTurn().endTurn();

        game.nextStep();

        game.executePlayerCharacterAbility(game.getActualRound().getActualTurn().getPlayer(), c.getId());
        System.out.println(game.rounds);
        game.getActualRound().getActualTurn().endTurn();

        game.nextStep();

        game.executePlayerCharacterAbility(game.getActualRound().getActualTurn().getPlayer(), c.getId());
        System.out.println(game.rounds);
        game.getActualRound().getActualTurn().endTurn();

        game.nextStep();

        game.executePlayerCharacterAbility(game.getActualRound().getActualTurn().getPlayer(), c.getId());
        System.out.println(game.rounds);
        game.getActualRound().getActualTurn().endTurn();

        game.nextStep();

        game.executePlayerCharacterAbility(game.getActualRound().getActualTurn().getPlayer(), c.getId());
        System.out.println(game.rounds);
        game.getActualRound().getActualTurn().endTurn();

        game.nextStep();

        game.executePlayerCharacterAbility(game.getActualRound().getActualTurn().getPlayer(), c.getId());
        System.out.println(game.rounds);
        game.getActualRound().getActualTurn().endTurn();

        game.nextStep();

        game.executePlayerCharacterAbility(game.getActualRound().getActualTurn().getPlayer(), c.getId());
        System.out.println(game.rounds);
        game.getActualRound().getActualTurn().endTurn();
    }
}
