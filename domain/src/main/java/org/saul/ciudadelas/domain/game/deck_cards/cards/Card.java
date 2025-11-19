package org.saul.ciudadelas.domain.game.deck_cards.cards;

import org.saul.ciudadelas.domain.game.deck_cards.Color;

public class Card implements Comparable<Card> {

    private Long id;
    private String name;
    private Color color;
    private boolean undestructible;

    public Card(Long id, String name, Color color, boolean undestructible) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.undestructible = undestructible;
    }

    public Color getColor() {
        return color;
    }

    public boolean isUndestructible() {
        return undestructible;
    }

    public Long getId() {
        return id;
    }




    @Override
    public int compareTo(Card o) {
        return this.id.compareTo(o.id);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                '}';
    }
}
