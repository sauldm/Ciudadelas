package org.saul.ciudadelas.domain.game.players;

import org.saul.ciudadelas.domain.exception.InternalGameException;
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

    public Player(Long id, String nickName, DeckCards characterCards) {
        this.id = id;
        this.nickName = nickName;
        this.characterCards = characterCards;
        this.gold = 2L;
        this.points = 0;
    }

    public boolean removeGold(int quantity){
        if (gold < quantity) return false;
        gold -= quantity;
        return true;
    }

    public boolean haveThisCard(Card card){
        if (card == null) throw new InternalGameException("La carta no puede ser nula");
        return characterCards.haveThisCard(card);
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
                ", districtDeckCardsInHand=" + districtDeckCardsInHand +
                ", districtDeckCardsBuilt=" + districtDeckCardsBuilt +
                ", characterCards=" + characterCards +
                ", gold=" + gold +
                ", points=" + points +
                '}';
    }
}
