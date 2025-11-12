package org.saul.ciudadelas.domain.game.deck_cards.actions;

import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.OtherPlayerDistrictBuiltActionCharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.cards.DistrictCard;

public class MilitaryActionCard extends CharacterCard implements OtherPlayerDistrictBuiltActionCharacterCard {
    public MilitaryActionCard(Long id) {
        super(id);
    }


    @Override
    public void execute(Game game, DistrictCard districtCard) {
        if (districtCard == null) throw new InternalGameException("El distrito no puede ser nulo");
        game.destroyDistrictOfOtherPlayer(districtCard);
    }
}
