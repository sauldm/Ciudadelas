package org.saul.ciudadelas.domain.game.deck_cards.actions;

import org.saul.ciudadelas.domain.exception.ExpectedGameError;
import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.cards.DistrictCard;
import org.saul.ciudadelas.domain.game.deck_cards.OptionalEpicCard;
import org.saul.ciudadelas.domain.game.players.Player;

public class TakeThreeActionCard extends DistrictCard implements OptionalEpicCard {

    @Override
    public void execute(Game game, Player player) {
        if (!player.removeGold(2)) throw new ExpectedGameError("No tienes monedas suficientes");
        player.addDistrictCards(game.getDistrictCards(3));
    }

}
