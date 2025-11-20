package org.saul.ciudadelas.domain.game.deck_cards.actions;

import org.saul.ciudadelas.domain.game.deck_cards.Color;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;

public class MerchantActionCard extends CharacterCard {
    public MerchantActionCard() {
        super(6L,"Merchant", Color.GREEN,false,1);
    }
}
