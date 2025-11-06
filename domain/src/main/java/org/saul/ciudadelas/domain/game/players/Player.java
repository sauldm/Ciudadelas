package org.saul.ciudadelas.domain.game.players;

import org.saul.ciudadelas.domain.game.Turn;
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
    private DeckCards characterCardsPlayed;
    private Long gold;
    private int points;
    private boolean isPlaying;

    public Player(Long id, String nickName) {
        this.id = id;
        this.nickName = nickName;
        this.gold = 2L;
        this.points = 0;
        this.characterCards = new DeckCards();
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

    public void addDistrictCards(List<Card> districtCards){
        districtDeckCardsInHand.addCards(districtCards);
    }

    public void addGold(Long quantity){
        gold += quantity;
    }

    public void addCharacterCard(CharacterCard characterCard){
        this.characterCards.addCard(characterCard);
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

    public boolean haveCharacter(CharacterCard characterCard) {
        return characterCards.haveThisCard(characterCard);
    }

    public void startPlaying(Turn turn, CharacterCard characterCard) {

    }


    public void turnSkipped(Turn turn, CharacterCard characterCard) {
        characterCardsPlayed.addCard(characterCards.getCard(characterCard));
        isPlaying = false;
    }
}
