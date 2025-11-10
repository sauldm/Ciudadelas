package org.saul.ciudadelas.domain.game;

import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;

import java.util.function.Consumer;
import java.util.function.Function;

public class RoundEvent {

    private Long characterTrigger;
    private Runnable eventEffect;

    public RoundEvent(Long characterTrigger, Runnable eventEffect) {
        this.characterTrigger = characterTrigger;
        this.eventEffect = eventEffect;
    }

    public Long getCharacterTrigger() {
        return characterTrigger;
    }

    public void trigerEvent() {
        eventEffect.run();
    }
}
