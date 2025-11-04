package org.saul.ciudadelas.domain.game.deck_cards.cards;

import java.util.Objects;

public class CharacterCard extends Card implements Comparable<CharacterCard>{

    private Long id;


    @Override
    public int compareTo(CharacterCard o) {
        return Long.compare(id, o.id);
    }
}
