package org.saul.ciudadelas.domain.game;

import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.deck_cards.Color;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.cards.DistrictCard;
import org.saul.ciudadelas.domain.game.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Turn implements Comparable<Turn>{
    private final Player player;
    private final CharacterCard characterCard;
    private boolean canPlay = true;
    private boolean isPlaying = false;
    private boolean isCompleted = false;
    private boolean isCharacterHabilityUsed = false;
    private int districtsBuiltThisTurn = 0;
    private List<Long> districtsHabilityUsedThisRound;

    private boolean isRobbed = false;

    private boolean isEliminated = false;
    private List<DistrictCard> districtCardsGained;


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
    public List<Long> getDistrictsHabilityUsedThisRound(){
        return districtsHabilityUsedThisRound;
    }

    public void startTurn(Game game){
        if (!canPlay){
            return;
        }
        if (characterCard.getColor() != Color.GREY)
            collectCoinsPerDistrictColor(game);
        isPlaying = true;
        if (!getPlayer().getCharacterCardsPlayed().contains(getCharacter()))
            getPlayer().addCharacterPlayed(getCharacter());
        executeDistrictsHabilitiesAtTurnStart(game);
        executeCharacterHabilitiesAtTurnStart(game);
    }

    public void skipTurn(){
        canPlay = false;
        isPlaying = false;
        isEliminated = true;
        isCompleted = true;
    }

    public void executeCharacterHability(Game game, Long characterCardActionId, Long targetId){
        if (characterCardActionId == null) throw new InternalGameException("La carta no puede ser nula");
        if (targetId == null) throw new InternalGameException("La carta no puede ser nula");
        if (!getCharacterId().equals(characterCardActionId)) throw new InternalGameException("El turno no corresponde a esa carta"+getCharacterId());
        if (isCharacterHabilityUsed()){
            game.getEventsBuffer().add(new EventMessage(Events.IMPOSIBLE_ACTION,"Ya has usado esta habilidad"));
        }
        getCharacter().executeCharacterAbility(game, targetId, player);
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

    public void collectCoinsPerDistrictColor(Game game) {
        Long goldCollectedPerDistrict = player.getIntDistrictsWithSameColor(characterCard.getColor());
        if (goldCollectedPerDistrict == 0L) return;
        player.addGold(goldCollectedPerDistrict);
        game.getEventsBuffer().add(new EventMessage(Events.MESSAGE,player.getNickName()+" ha obtenido "+goldCollectedPerDistrict+" oro"));
    }

    public void incrementDistrictsBuiltThisTurn() {
        districtsBuiltThisTurn++;
    }

    public void executeCharacterHabilitiesAtTurnStart(Game game){
        CharacterCard card = player.findCharacterWithHabilityAtTurnStart();

        if (card == null) return;
        if (!Objects.equals(card.getId(), getCharacter().getId())) return;

        card.executeCharacterAbility(game, player.getId(), player);
        if (isCharacterHabilityUsed) throw new InternalGameException("Ya se ha ejecutado esta habilidad");
        characterHabilityUsed();
    }

    public void executeDistrictsHabilitiesAtTurnStart(Game game) {
        if(player.findDistrictsWithHabilityAtTurnStart().isEmpty()) return;
        player.findDistrictsWithHabilityAtTurnStart().forEach(districtCard -> {
            if (districtUsed(districtCard.getId())){
                throw new InternalGameException("Ya se ha ejecutado esta habilidad");
            }
            districtCard.executeDistrictAbility(game, player);
            addDistrictUsedThisRound(districtCard.getId());
        });
    }

    public void executeDistrictAbility(Game game, Long districtCardId) {
        if (districtUsed(districtCardId)){
            game.getEventsBuffer().add(new EventMessage(Events.IMPOSIBLE_ACTION, "Ya has usado esta habilidad"));
            return;
        }

        Player player = game.findPlayerByDistrictCardIdBuilt(districtCardId);
        if (player == null) throw new InternalGameException("El jugador no puede ser nulo");
        if (player != getPlayer()) throw new InternalGameException("El jugador no puede usar la habilidad de otro jugador");

        DistrictCard districtCard = player.findDistrictCardBuilt(districtCardId);
        if (districtCard == null) throw new InternalGameException("La carta no puede ser nula");

        districtCard.executeDistrictAbility(game, player);
    }

    public void playerAddCoins(Long turnPlayerGold) {
        if (!isCompleted)
            player.addGold(turnPlayerGold);
        isCompleted = true;
    }

    public void playerAddDistrictCard(Game game,Integer turnDistrictCardPlayer) {
        if (!isCompleted)
            player.addDistrictCardsInHand(game.getDistrictCards(turnDistrictCardPlayer));
        isCompleted = true;
    }

    public void characterRobbed() {
        isRobbed = true;
    }

    public void setDistrictCardsGainedInTurn(List<DistrictCard> districtCardsGained) {
        this.districtCardsGained.addAll(districtCardsGained);
    }
}
