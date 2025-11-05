package org.saul.ciudadelas.domain.game;

import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.deck_cards.cards.Card;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;
import org.saul.ciudadelas.domain.game.players.Player;

import java.util.List;
import java.util.TreeSet;

public class Round {
    private TreeSet<Turn> turns;
    private CharacterCard characterRobed;
    private CharacterCard characterThief;

    public Round() {
        this.turns = new TreeSet<>();
    }

    public void addTurn(Player player,CharacterCard characterCard){
        turns.add(new Turn(player,characterCard));
    }

    @Override
    public String toString() {
        return "Turnos: "+turns;
    }
}
