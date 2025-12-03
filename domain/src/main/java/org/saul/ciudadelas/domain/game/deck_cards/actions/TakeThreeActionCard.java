package org.saul.ciudadelas.domain.game.deck_cards.actions;

import org.saul.ciudadelas.domain.exception.ExpectedGameError;
import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.Color;
import org.saul.ciudadelas.domain.game.deck_cards.cards.DistrictCard;
import org.saul.ciudadelas.domain.game.deck_cards.OptionalEpicCard;
import org.saul.ciudadelas.domain.game.players.Player;

public class TakeThreeActionCard extends DistrictCard implements OptionalEpicCard {

    public TakeThreeActionCard(Long id) {
        super(id,"Take Three Action", Color.PURPLE, false,"TakeTh",5L, 6);
    }

    @Override
    public void execute(Game game, Player player) {
        if (!player.removeGold(2L)) throw new ExpectedGameError("No tienes monedas suficientes");
        player.addDistrictCardsInHand(game.getDistrictCards(3));
    }

}
