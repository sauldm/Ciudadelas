package org.saul.ciudadelas.domain.game.deck_cards;

public enum WizardTarget {
    GAMEDECK,
    PLAYER;


    public static WizardTarget fromValue(Long value) {
        return value == 0 ? GAMEDECK : PLAYER;
    }

}
