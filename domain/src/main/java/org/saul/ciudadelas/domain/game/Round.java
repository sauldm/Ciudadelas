package org.saul.ciudadelas.domain.game;

import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;

import java.util.List;
import java.util.Optional;

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
        round.turns.getFirst().startTurn();
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
        turns.get(actualTurn).startTurn();
        // Enviar evento al front de nuevo turno
        trigerEvents();
        return true;
    }

    private void trigerEvents() {
        if (roundEvents.isEmpty()) return;
        Long characterId = getActualTurn().getCharacterId();
        roundEvents.stream()
                .filter(event -> event.getCharacterTrigger().equals(characterId))
                .forEach(RoundEvent::trigerEvent) //Lanzar evento al front de nuevo evento;
                ;
    }

    public void skipCharacterTurn(CharacterCard characterCard) {
        Optional<Turn> turnOptional = turns.stream()
                .filter(turn -> turn.getCharacterId().equals(characterCard.getId()))
                .findFirst();
        if (turnOptional.isEmpty()) throw new InternalGameException("El turno tiene que existir en la ronda");
        Turn turn = turnOptional.get();
        turn.stopPlaying();
    }

    public Turn getTurnByCharacter(CharacterCard characterCard){
        Optional<Turn> turnOptional = turns.stream()
                .filter(turn -> turn.getCharacterId().equals(characterCard.getId()))
                .findFirst();
        if (turnOptional.isEmpty()) throw new InternalGameException("El turno tiene que existir en la ronda");
        return turnOptional.get();
    }



    @Override
    public String toString() {
        return "Turnos: "+turns;
    }


}
