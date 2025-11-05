package org.saul.ciudadelas.domain.game.deck_cards.actions;

import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.OtherPlayerActionCharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;

public class KingActionCard extends CharacterCard implements OtherPlayerActionCharacterCard {
    public KingActionCard(Long id) {
        super(id);
    }

    @Override
    public void execute(Game game, CharacterCard characterCard) {

    }
}
