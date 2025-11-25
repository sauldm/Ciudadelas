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

    public boolean isLastTurn(){
        return actualTurn == (turns.size() -1);
    }

    public void nextTurn(Game game){
        turns.get(actualTurn).endTurn();
        actualTurn++;
        startTurn(game);
        // Enviar evento al front de nuevo turno
    }

    private void startTurn(Game game) {
        getActualTurn().startTurn();
        trigerTurnEvents(game);
        getActualTurn().executeDistrictsHabilitiesAtTurnStart(game);
    }

    public void trigerTurnEvents(Game game) {
        if (roundEvents == null || roundEvents.isEmpty()) return;
        Long characterId = getActualTurn().getCharacterId();
        roundEvents.stream()
        .filter(event -> event.getCharacterTrigger().equals(characterId))
        .forEach(event -> event.trigerEvent(game));
        ; //Lanzar el evento de nuevo evento;
    }



    public void skipCharacterTurn(Long characterCardId) {
        Turn turn = getActualTurn();
        turn.endTurn();
        System.out.println("Se ha saltado el turno del personaje: " + characterCardId);
    }

    public boolean characterIsNotInRound(Long characterCardId){
        Optional<Turn> turnOptional = turns.stream()
                .filter(turn -> turn.getCharacterId().equals(characterCardId))
                .findFirst();
        return turnOptional.isEmpty();
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
        return turnOptional.map(Turn::getCharacter).orElse(null);
    }

    public int getDistrictsBuiltThisTurn() {
        return getActualTurn().getDistrictsBuiltThisTurn();
    }

    public void incrementDistrictsBuiltThisTurn() {
        getActualTurn().incrementDistrictsBuiltThisTurn();
    }


    @Override
    public String toString() {
        return "Round{" +
                "turns=" + turns +
                ", actualTurn=" + actualTurn +
                ", roundEvents=" + roundEvents +
                '}';
    }

    public void playerAddCoins(Long turnPlayerGold) {
        getActualTurn().playerAddCoins(turnPlayerGold);
    }

    public void playerAddDistrictCard(Game game,Integer turnDistrictCardPlayer) {
        getActualTurn().playerAddDistrictCard(game,turnDistrictCardPlayer);
    }
}
