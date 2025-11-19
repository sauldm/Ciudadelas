package org.saul.ciudadelas.domain.game.deck_cards.actions;

import org.saul.ciudadelas.domain.game.deck_cards.Color;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;

public class BishopActionCard extends CharacterCard {
    public BishopActionCard() {
        super(5L,"Bishop", Color.BLUE, true);
    }
}
