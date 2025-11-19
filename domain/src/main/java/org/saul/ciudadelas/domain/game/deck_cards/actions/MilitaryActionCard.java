package org.saul.ciudadelas.domain.game.deck_cards.actions;

import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.Color;
import org.saul.ciudadelas.domain.game.deck_cards.OtherPlayerActionCharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;

public class MilitaryActionCard extends CharacterCard implements OtherPlayerActionCharacterCard {
    public MilitaryActionCard() {
        super(8L,"Military", Color.RED,false);
    }


    @Override
    public void execute(Game game, Long districtCardId) {
        if (districtCardId == null) throw new InternalGameException("El distrito no puede ser nulo");
        game.destroyDistrictOfOtherPlayer(districtCardId,this);
    }
}
