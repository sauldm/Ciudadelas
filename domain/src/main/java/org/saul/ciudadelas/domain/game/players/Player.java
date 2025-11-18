package org.saul.ciudadelas.domain.game.players;

import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.cards.DistrictCard;

import java.util.List;

import static org.saul.ciudadelas.domain.game.GameConstants.INITIAL_PLAYER_GOLD;

public class Player {
    private final Long id;
    private final String nickName;
    private final DeckCards<DistrictCard> districtDeckCardsInHand = new DeckCards<>();
    private DeckCards<DistrictCard> districtDeckCardsBuilt;
    private final DeckCards<CharacterCard> characterCards;
    private Long gold;
    private int points;

    public Player(Long id, String nickName) {
        this.id = id;
        this.nickName = nickName;
        this.gold = INITIAL_PLAYER_GOLD;
        this.points = 0;
        this.characterCards = new DeckCards<>();
        this.districtDeckCardsBuilt = new DeckCards<>();
    }


    public boolean removeGold(int quantity){
        if (gold < quantity) return false;
        gold -= quantity;
        return true;
    }

    public Long getAllGold(){
        Long stoledGold = gold;
        gold = 0L;
        return stoledGold;

    }

    public void addDistrictCards(List<DistrictCard> districtCards){
        districtDeckCardsInHand.addCards(districtCards);
    }

    public List<DistrictCard> getAllDistrictCardsInHand(){
        return districtDeckCardsInHand.getAllCards();
    }

    public void addGold(Long quantity){
        gold += quantity;
    }

    public void addCharacterCard(CharacterCard characterCard){
        this.characterCards.addCard(characterCard);
    }

    public CharacterCard haveCharacter(Long characterCardId) {
        return characterCards.haveThisCard(characterCardId);
    }

    public List<CharacterCard> clearCharacterCards() {
        return characterCards.getAllCards();
    }

    public boolean haveDistrictCard(Long districtCardId) {
        return districtDeckCardsBuilt.haveThisCard(districtCardId) != null;
    }

    public DistrictCard getDistrictCardFromHand(Long districtCardId) {
        if (districtCardId == null) throw new IllegalArgumentException("El distrito no puede ser nulo");
        DistrictCard districtCard = districtDeckCardsInHand.haveThisCard(districtCardId);
        return districtDeckCardsInHand.getCard(districtCard);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", characterCards=" + characterCards +
                ", gold=" + gold +
                '}';
    }


}
