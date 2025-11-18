package org.saul.ciudadelas.domain.game.deck_cards;

public enum WizardTarget {
    GAMEDECK(0L),
    PLAYER(-1L);

    private Long value;

    WizardTarget(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public static WizardTarget fromValue(Long value) {
        for (WizardTarget target : values()) {
            if (!target.getValue().equals(0L)) {
                target.value = value;
            }
            return target;
        }
        throw new IllegalArgumentException("Invalid WizardTarget value: " + value);
    }

}
