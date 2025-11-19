package org.saul.ciudadelas.domain.game.deck_cards.cards;

import org.saul.ciudadelas.domain.game.deck_cards.Color;

public class DistrictCard extends Card{

    private Long price;
    private Color color;


    public DistrictCard(Long id, String name, Color color,boolean undestructible, Long price) {
        super(id, name, color, undestructible);
        this.price = price;
    }

    public Long getPrice() {
        return price;
    }
}
