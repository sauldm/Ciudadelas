package org.saul.ciudadelas.domain.game.deck_cards;

import org.saul.ciudadelas.domain.game.Game;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;

public interface OtherPlayerActionCharacterCard {

    void execute(Game game, CharacterCard characterCard);
}
