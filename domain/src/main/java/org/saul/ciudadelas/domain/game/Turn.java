package org.saul.ciudadelas.domain.game;

import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;
import org.saul.ciudadelas.domain.game.players.Player;

public class Turn {
    private Player player;
    private CharacterCard characterCard;
    private boolean canPlay = true;

    public void skipTurn(){
        this.canPlay = false;

    }

    public boolean canPlayerPlay(){
        return this.canPlay;
    }

}
