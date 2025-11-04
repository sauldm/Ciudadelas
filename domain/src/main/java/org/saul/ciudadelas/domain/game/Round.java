package org.saul.ciudadelas.domain.game;

import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.deck_cards.cards.Card;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;

import java.util.List;

public class Round {
    List<Turn> turns;
    CharacterCard characterRobed;
    CharacterCard characterThief;


    public Round(List<Card> orderOfCharacters) {
        this.turns = List.of();
    }

    public void passTurn(CharacterCard characterCard){
        if (characterCard == null) throw new InternalGameException("La carta no puede ser null");
    }

}
