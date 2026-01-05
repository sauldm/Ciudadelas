package org.saul.ciudadelas.domain.game.players;

import org.saul.ciudadelas.domain.game.deck_cards.Color;
import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;
import org.saul.ciudadelas.domain.game.deck_cards.StartTurnActionCard;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.cards.DistrictCard;

import java.util.ArrayList;
import java.util.List;

import static org.saul.ciudadelas.domain.game.GameConstants.INITIAL_PLAYER_GOLD;

public class Player{
    private Long id;
    private final String nickName;
    private final DeckCards<DistrictCard> districtDeckCardsInHand = new DeckCards<>();
    private DeckCards<DistrictCard> districtDeckCardsBuilt;
    private final DeckCards<CharacterCard> characterCards;
    private Long gold;
    private int points;
    private List<CharacterCard> characterCardsPlayed;
    private List<DistrictCard> privateDistrictGained;
    private Integer wins;

    public Player(Long id,String nickName, Integer wins) {
        this.id = id;
        this.nickName = nickName;
        this.gold = INITIAL_PLAYER_GOLD;
        this.points = 0;
        this.characterCards = new DeckCards<>();
        this.districtDeckCardsBuilt = new DeckCards<>();
        this.characterCardsPlayed = new ArrayList<>();
        this.wins = wins == null ? 0 : wins;
    }

    public Long getId() {
        return id;
    }

    public Long getGold(){
        return gold;
    }

    public DeckCards<DistrictCard> getDistrictDeckCardsInHand() {
        return districtDeckCardsInHand;
    }

    public void setPrivateDistrictGained(List<DistrictCard> privateDistrictGained) {
        this.privateDistrictGained = privateDistrictGained;
    }

    public List<DistrictCard> getPrivateDistrictGained() {
        return privateDistrictGained;
    }

    public String getNickName() {
        return nickName;
    }

    public DeckCards<DistrictCard> getDistrictDeckCardsBuilt() {
        return districtDeckCardsBuilt;
    }

    public DeckCards<CharacterCard> getCharacterCards() {
        return characterCards;
    }

    public List<CharacterCard> getCharacterCardsPlayed(){
        return characterCardsPlayed;
    }

    public int getPoints() {
        return points;
    }

    public Integer getWins() {
        return wins;
    }

    public boolean removeGold(Long quantity){
        if (gold < quantity) return false;
        gold -= quantity;
        return true;
    }

    public Long getAllGold(){
        Long stoledGold = gold;
        gold = 0L;
        return stoledGold;

    }

    public void addCharacterPlayed(CharacterCard characterCard){
        characterCardsPlayed.add(characterCard);
    }

    public void addGold(Long quantity){
        gold += quantity;
    }

    public void addDistrictCardsInHand(List<DistrictCard> districtCards){
        districtDeckCardsInHand.addCards(districtCards);
    }

    public List<DistrictCard> getAllDistrictCardsInHand(){
        return districtDeckCardsInHand.getAllCards();
    }

    public void addCharacterCard(CharacterCard characterCard){
        this.characterCards.addCard(characterCard);
    }

    public CharacterCard haveCharacter(Long characterCardId) {
        return characterCards.haveThisCard(characterCardId);
    }

    public CharacterCard findCharacterUndestructible() {
        for (CharacterCard characterCard : characterCards.orderCards()) {
            if (characterCard.isUndestructible()) {
                return characterCard;
            }
        }
        return null;
    }

    public List<CharacterCard> clearCharacterCards() {
        characterCardsPlayed.clear();
        return characterCards.getAllCards();
    }

    public DistrictCard findDistrictCardBuilt(Long districtCardId) {
        return districtDeckCardsBuilt.haveThisCard(districtCardId);
    }

    public DistrictCard findDistrictCardInHand(Long districtCardId) {
        return districtDeckCardsInHand.haveThisCard(districtCardId);
    }

    public DistrictCard getDistrictCardFromHand(Long districtCardId) {
        if (districtCardId == null) throw new IllegalArgumentException("El distrito no puede ser nulo");
        DistrictCard districtCard = districtDeckCardsInHand.haveThisCard(districtCardId);
        return districtDeckCardsInHand.getCard(districtCard);
    }

    public DistrictCard getDistrictCardBuilt(Long districtCardId) {
        if (districtCardId == null) throw new IllegalArgumentException("El distrito no puede ser nulo");
        DistrictCard districtCard = districtDeckCardsBuilt.haveThisCard(districtCardId);
        return districtDeckCardsBuilt.getCard(districtCard);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", characterCards=" + characterCards +
                ", districtDeckCardsInHand=" + districtDeckCardsInHand +
                ", districtDeckCardsBuilt=" + districtDeckCardsBuilt +
                ", gold=" + gold +
                '}';
    }

    public Long getIntDistrictsWithSameColor(Color color) {
        return districtDeckCardsBuilt.getIntSameColorCards(color);
    }

    public void buildDistrictCard(DistrictCard districtCard) {
        districtDeckCardsBuilt.addCard(districtCard);
    }

    public List<DistrictCard> findDistrictsWithHabilityAtTurnStart() {
        return districtDeckCardsInHand.findCardsWithInstance(StartTurnActionCard.class);
    }

    public CharacterCard findCharacterWithHabilityAtTurnStart(){
        return characterCards.findCardWithInstance(StartTurnActionCard.class);
    }

    public int districtCardsBuilt() {
        return districtDeckCardsBuilt.size();
    }

    public void sumAllPoints(){
        points += districtDeckCardsBuilt.sumPoints();

    }

    public void sumPoints(int cuantity){
        points += cuantity;
    }

    public void addWin() {
        this.wins += 1;
    }
}
