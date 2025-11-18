package org.saul.ciudadelas.domain.game.deck_cards.cards;

public class Card implements Comparable<Card> {

    private Long id;
    private String name;
    private String color;
    private String ref;

    public Card(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }




    @Override
    public int compareTo(Card o) {
        return this.id.compareTo(o.id);
    }
}
