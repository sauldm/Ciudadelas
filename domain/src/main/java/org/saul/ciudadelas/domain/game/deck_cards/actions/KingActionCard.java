package org.saul.ciudadelas.domain.game.deck_cards.actions;
import org.saul.ciudadelas.domain.game.deck_cards.Color;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;

public class KingActionCard extends CharacterCard {
    public KingActionCard() {
        super(4L,"Gobernador", Color.YELLOW,false,"No puede ser asesinado",1,0L);
    }

}
