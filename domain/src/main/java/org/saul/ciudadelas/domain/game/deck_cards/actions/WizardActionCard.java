package org.saul.ciudadelas.domain.game.deck_cards.actions;

import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.OtherPlayerActionCharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;

public class WizardActionCard extends CharacterCard implements OtherPlayerActionCharacterCard {
    public WizardActionCard(Long id) {
        super(id);
    }


    @Override
    public void execute(Game game, CharacterCard characterCard) {
        if (characterCard == null) throw new InternalGameException("La carta no puede ser nula");
        game.swapHandsWithPlayer(characterCard,this);
    }
}
