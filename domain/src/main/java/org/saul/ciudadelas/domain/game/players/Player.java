package org.saul.ciudadelas.domain.game.players;

import org.saul.ciudadelas.domain.game.deck_cards.cards.Card;
import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;

import java.util.List;

public class Player {
    private Long id;
    private String nickName;
    private final DeckCards districtDeckCardsInHand = new DeckCards();
    private DeckCards districtDeckCardsBuilt;
    private DeckCards characterCards;
    private Long gold;
    private int points;

    public Player(Long id, String nickName, DeckCards characterCards, Long gold, int points) {
        this.id = id;
        this.nickName = nickName;
        this.characterCards = null;
        this.gold = 2L;
        this.points = 0;
    }

    public boolean removeGold(int quantity){
        if (gold < quantity) return false;
        gold -= quantity;
        return true;
    }

    public void addDistrictCards(List<Card> districtCards){
        districtDeckCardsInHand.addCards(districtCards);
    }

    public void addGold(int quantity){
        gold += quantity;
    }

    public void getCharacterCard(CharacterCard characterCard){
        this.characterCards.addCard(characterCard);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", districtDeckCards=" + districtDeckCardsInHand +
                ", gold=" + gold +
                '}';
    }
}
