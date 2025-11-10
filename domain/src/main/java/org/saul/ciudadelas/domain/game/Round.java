package org.saul.ciudadelas.domain.game;

import com.sun.source.tree.Tree;
import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.deck_cards.cards.Card;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;
import org.saul.ciudadelas.domain.game.players.Player;

import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

public class Round {
    private List<Turn> turns;
    private int actualTurn;
    private List<RoundEvent> roundEvents;


    private Round(List<Turn> turns) {
        this.turns = turns;
    }

    public static Round initializeRound(List<Turn> turns){
        Round round = new Round(turns);
        round.actualTurn = 0;
        round.turns.getFirst().startPlaying();
        return round;
    }

    public void addRoundEvent(RoundEvent roundEvent){
        roundEvents.add(roundEvent);
    }



    public Turn getActualTurn(){
        if (turns.isEmpty()) throw new InternalGameException("Tienen que haber turnos");
        return turns.get(actualTurn);
    }

    public boolean nextTurn(){
        turns.get(actualTurn).stopPlaying();
        if (turns.size() == (actualTurn + 1)) return false;
        actualTurn++;
        turns.get(actualTurn).startPlaying();
        // Enviar evento al front de nuevo turno
        return true;
    }




    @Override
    public String toString() {
        return "Turnos: "+turns;
    }
}
