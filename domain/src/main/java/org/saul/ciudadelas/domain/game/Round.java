package org.saul.ciudadelas.domain.game;

import com.sun.source.tree.Tree;
import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.deck_cards.cards.Card;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;
import org.saul.ciudadelas.domain.game.players.Player;

import java.util.List;
import java.util.TreeSet;

public class Round {
    private List<Turn> turns;
    private int actualTurn;

    public Round(List<Turn> turns) {
        this.turns = turns;
        actualTurn = 0;
    }



    public Turn getActualTurn(){
        if (turns.isEmpty()) throw new InternalGameException("Tienen que haber turnos");
        return turns.get(actualTurn);
    }

    //Acabar
    public void nextTurn(){
        actualTurn++;

    }


    @Override
    public String toString() {
        return "Turnos: "+turns;
    }
}
