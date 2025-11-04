package org.saul.ciudadelas.domain.game.deck_cards.actions;

import org.saul.ciudadelas.domain.exception.ExpectedGameError;
import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.OtherPlayerActionCharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;

public class ThiefActionCard extends CharacterCard implements OtherPlayerActionCharacterCard {
    @Override
    public void execute(Game game, CharacterCard characterCard) {
        if (characterCard == null) throw new InternalGameException("La carta no puede ser nula");
        if (characterCard.getClass().equals(AssassinActionCard.class)) throw new ExpectedGameError("La carta no puede ser un asesino");
        game.stoleCharacterGold(characterCard,this);
    }
}
