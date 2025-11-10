package org.saul.ciudadelas.domain.game;

import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;

import java.util.function.Consumer;

public class RoundEvent {

    private Long characterTrigger;
    private Consumer<Game> eventEffect;

    public RoundEvent(Long characterTrigger, Consumer<Game> eventEffect) {
        this.characterTrigger = characterTrigger;
        this.eventEffect = eventEffect;
    }
}
