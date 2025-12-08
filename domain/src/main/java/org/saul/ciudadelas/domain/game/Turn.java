package org.saul.ciudadelas.domain.game;

import org.saul.ciudadelas.domain.exception.ExpectedGameError;
import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.deck_cards.Color;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.cards.DistrictCard;
import org.saul.ciudadelas.domain.game.players.Player;

import java.util.ArrayList;
import java.util.List;

public class Turn implements Comparable<Turn>{
    private final Player player;
    private final CharacterCard characterCard;
    private boolean canPlay = true;
    private boolean isPlaying = false;
    private boolean isCompleted = false;
    private boolean isCharacterHabilityUsed = false;
    private int districtsBuiltThisTurn = 0;
    private List<Long> districtsHabilityUsedThisRound;


    public Turn(Player player, CharacterCard characterCard){
        this.player = player;
        this.characterCard = characterCard;
        this.districtsHabilityUsedThisRound = new ArrayList<>();
    }

    public boolean districtUsed(Long districtCardId) {
        return districtsHabilityUsedThisRound.contains(districtCardId);
    }

    public void addDistrictUsedThisRound(Long districtCardId) {
        districtsHabilityUsedThisRound.add(districtCardId);
    }

    public int getDistrictsBuiltThisTurn() {
        return districtsBuiltThisTurn;
    }

    public boolean canPlayerPlay(){
        return canPlay;
    }

    public boolean isCharacterHabilityUsed(){
        return isCharacterHabilityUsed;
    }

    public void characterHabilityUsed(){
        isCharacterHabilityUsed = true;
    }

    public Long getCharacterId(){
        return characterCard.getId();
    }

    public boolean isTurnCompleted(){
        return isCompleted;
    }

    public void startTurn(){
        if (characterCard.getColor() != Color.GREY)
            collectCoinsPerDistrictColor(); // Enviar evento al frontend
        isPlaying = true;
    }

    public void endTurn(){
        isCompleted = true;
        isPlaying = false;
        canPlay = false;
    }

    public void executeCharacterHability(Game game, Long characterCardActionId, Long targetId){
        if (characterCardActionId == null) throw new InternalGameException("La carta no puede ser nula");
        if (targetId == null) throw new InternalGameException("La carta no puede ser nula");
        if (!getCharacterId().equals(characterCardActionId)) throw new InternalGameException("El turno no corresponde a esa carta"+getCharacterId());
        if (isCharacterHabilityUsed()) throw new ExpectedGameError("No puede usar otra vez la habilidad");//Enviar evento al frontend;

        getCharacter().executeCharacterAbility(game, targetId);
    }


    @Override
    public int compareTo(Turn o) {
        return this.characterCard.compareTo(o.characterCard);
    }

    @Override
    public String toString() {
        return "Turn{" +
                "player=" + player +
                ", characterCard=" + characterCard +
                ", isPlaying=" + isPlaying +
                '}';
    }

    public Player getPlayer() {
        return player;
    }

    public CharacterCard getCharacter() {
        return characterCard;
    }

    public void collectCoinsPerDistrictColor() {
        Long goldCollectedPerDistrict = player.getIntDistrictsWithSameColor(characterCard.getColor());
        player.addGold(goldCollectedPerDistrict); //Enviar evento al frontend
    }

    public void incrementDistrictsBuiltThisTurn() {
        districtsBuiltThisTurn++;
    }

    public void executeDistrictsHabilitiesAtTurnStart(Game game) {
        if(player.findDistrictsWithHabilityAtTurnStart().isEmpty()) return; //Enviar evento al frontend
        player.findDistrictsWithHabilityAtTurnStart().forEach(districtCard -> {
            if (districtUsed(districtCard.getId())) return; //Enviar evento al frontend
            districtCard.executeDistrictAbility(game, player);
            addDistrictUsedThisRound(districtCard.getId());
        });
    }

    public void executeDistrictAbility(Game game, Long districtCardId) {
        if (districtUsed(districtCardId)) return; //Enviar evento al frontend

        Player player = game.findPlayerByDistrictCardIdBuilt(districtCardId);
        if (player == null) throw new InternalGameException("El jugador no puede ser nulo");
        if (player != getPlayer()) throw new InternalGameException("El jugador no puede usar la habilidad de otro jugador");

        DistrictCard districtCard = player.findDistrictCardBuilt(districtCardId);
        if (districtCard == null) throw new InternalGameException("La carta no puede ser nula");

        districtCard.executeDistrictAbility(game, player);
        addDistrictUsedThisRound(districtCardId);
    }

    public void playerAddCoins(Long turnPlayerGold) {
        player.addGold(turnPlayerGold);
    }

    public void playerAddDistrictCard(Game game,Integer turnDistrictCardPlayer) {
        player.addDistrictCardsInHand(game.getDistrictCards(turnDistrictCardPlayer));
    }
}
