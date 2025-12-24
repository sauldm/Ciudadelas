package org.saul.ciudadelas.domain.game.deck_cards.cards;

import org.saul.ciudadelas.domain.game.deck_cards.Color;

public class Card implements Comparable<Card> {

    private Long id;
    private String name;
    private Color color;
    private boolean undestructible;
    private String description;

    private Long price;

    public Card(Long id, String name, Color color, boolean undestructible, String description, Long price) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.undestructible = undestructible;
        this.description = description;
        this.price = price;
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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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


    public Long getPrice() {
        return this.price;
    }
}
