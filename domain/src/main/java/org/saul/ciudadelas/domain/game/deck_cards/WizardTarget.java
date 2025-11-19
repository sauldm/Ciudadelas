package org.saul.ciudadelas.domain.game.deck_cards;

public enum WizardTarget {
    GAMEDECK(0L),
    PLAYER(-1L);

    private Long value;

    WizardTarget(Long value) {
        this.value = value;
    }


    public static WizardTarget fromValue(Long value) {
        return value == 0 ? GAMEDECK : PLAYER;
    }

}
