package org.saul.ciudadelas.domain.game.deck_cards.actions;

import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.Color;
import org.saul.ciudadelas.domain.game.deck_cards.cards.DistrictCard;
import org.saul.ciudadelas.domain.game.deck_cards.FinalGameEpicCard;
import org.saul.ciudadelas.domain.game.players.Player;

public class TwoPointsFinalActionCard extends DistrictCard implements FinalGameEpicCard {

    public TwoPointsFinalActionCard(Long id) {
        super(id,"Two Points Final Action", Color.PURPLE,false, 6L);
    }

    @Override
    public void execute(Game game, Player player) {

    }
}
