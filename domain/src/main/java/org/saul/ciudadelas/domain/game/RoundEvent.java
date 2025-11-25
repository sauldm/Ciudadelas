package org.saul.ciudadelas.domain.game;

import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;

import java.util.function.Consumer;
import java.util.function.Function;

public class RoundEvent {

    private Long characterTrigger;
    private Consumer<Game> eventEffect;

    public RoundEvent(Long characterTrigger, Consumer<Game> eventEffect) {
        this.characterTrigger = characterTrigger;
        this.eventEffect = eventEffect;
    }

    public Long getCharacterTrigger() {
        return characterTrigger;
    }

    public void trigerEvent(Game game) {
        eventEffect.accept(game);

    }

    @Override
    public String toString() {
        return "RoundEvent{" +
                "characterTrigger=" + characterTrigger +
                ", eventEffect=" + eventEffect +
                '}';
    }
}
