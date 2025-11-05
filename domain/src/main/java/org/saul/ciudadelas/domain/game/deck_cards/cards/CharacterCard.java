package org.saul.ciudadelas.domain.game.deck_cards.cards;

import java.util.Objects;

public class CharacterCard extends Card implements Comparable<CharacterCard>{

    private Long id;

    public CharacterCard(Long id){
        this.id = id;
    }

    @Override
    public String toString() {
        return "ID:" + id;
    }

    @Override
    public int compareTo(CharacterCard o) {
        return Long.compare(id, o.id);
    }
}
