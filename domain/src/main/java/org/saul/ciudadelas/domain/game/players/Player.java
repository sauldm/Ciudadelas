package org.saul.ciudadelas.domain.game.players;

import org.saul.ciudadelas.domain.game.deck_cards.cards.Card;
import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;

import java.util.List;

public class Player {
    private Long id;
    private String nickName;
    private final DeckCards districtDeckCardsInHand = new DeckCards();
    private DeckCards districtDeckCardsBuilt;
    private DeckCards characterCards;
    private Long gold = 0L;
    private int points;

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
