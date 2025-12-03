package org.saul.ciudadelas.domain.game.deck_cards.actions;

import org.saul.ciudadelas.domain.game.deck_cards.Color;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;

public class ArchitectActionCard extends CharacterCard {
    public ArchitectActionCard() {
        super(7L,"Architect", Color.GREY,false, "arc",2);
    }
}
