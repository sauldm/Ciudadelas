package org.saul.ciudadelas.domain.game.deck_cards.actions;

import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.RoundEvent;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.OtherPlayerActionCharacterCard;

public class AssassinActionCard extends CharacterCard implements OtherPlayerActionCharacterCard {

    public AssassinActionCard(Long id) {
        super(id);
    }

    @Override
    public void execute(Game game, CharacterCard characterCard) {
        if (characterCard == null) throw new InternalGameException("La carta no puede ser nula");
        RoundEvent event = new RoundEvent(characterCard.getId(), () -> {
            game.stopCharacterPlaying(characterCard);
        });
        game.addRoundEvent(event);
    }
}
