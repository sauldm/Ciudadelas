package org.saul.ciudadelas.domain.game;

import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;

import java.util.ArrayList;
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
        round.roundEvents = new ArrayList<>();

        return round;
    }

    public void addRoundEvent(RoundEvent roundEvent){
        System.out.println("Se añade un nuevo evento a la ronda: " + roundEvent);
        if (roundEvent == null) throw new InternalGameException("El evento no puede ser nulo");
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
        if (roundEvents == null || roundEvents.isEmpty()) return; // Arreglar
        Long characterId = getActualTurn().getCharacterId();
        roundEvents.stream()
        .filter(event -> event.getCharacterTrigger().equals(characterId))
                .forEach(RoundEvent::trigerEvent); //Lanzar el evento de nuevo evento;
    }

    public void skipCharacterTurn(Long characterCardId) {
        Optional<Turn> turnOptional = turns.stream()
                .filter(turn -> turn.getCharacterId().equals(characterCardId))
                .findFirst();
        if (turnOptional.isEmpty()) throw new InternalGameException("El turno tiene que existir en la ronda");
        Turn turn = turnOptional.get();
        turn.stopPlaying();
        System.out.println("Se ha saltado el turno del personaje: " + characterCardId);
    }

    public Turn getTurnByCharacter(Long characterCardId){
        Optional<Turn> turnOptional = turns.stream()
                .filter(turn -> turn.getCharacterId().equals(characterCardId))
                .findFirst();
        if (turnOptional.isEmpty()) throw new InternalGameException("El turno tiene que existir en la ronda");
        return turnOptional.get();
    }

    public CharacterCard findCharacterById(Long characterCardId){
        Optional<Turn> turnOptional = turns.stream()
                .filter(turn -> turn.getCharacterId().equals(characterCardId))
                .findFirst();
        if (turnOptional.isEmpty()) throw new InternalGameException("El turno tiene que existir en la ronda");
        return turnOptional.get().getCharacter();
    }



    @Override
    public String toString() {
        return "Round{" +
                "turns=" + turns +
                ", actualTurn=" + actualTurn +
                ", roundEvents=" + roundEvents +
                '}';
    }
}
