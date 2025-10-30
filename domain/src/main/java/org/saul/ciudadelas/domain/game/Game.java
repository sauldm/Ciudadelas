package org.saul.ciudadelas.domain.game;

import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.deck_cards.OtherPlayerActionCharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.cards.Card;
import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;
import org.saul.ciudadelas.domain.game.players.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    private final DeckCards deckDistrictCards;
    private List<Player> players;
    private DeckCards deckCharacterCards;
    private int rondas;
    private List<Turn> turns;
    public List<Player> playerChooseOrder;
    private int indexPlayerSelecting;


    public Game(DeckCards deckDistrictCards, List<Player> players, DeckCards deckCharacterCards) {
        this.deckDistrictCards = deckDistrictCards;
        this.players = players;
        this.deckCharacterCards = deckCharacterCards;
        this.rondas = 1;
        this.turns = new ArrayList<>();
        this.playerChooseOrder = new ArrayList<>(players);
        this.indexPlayerSelecting = 0;


        this.players.forEach(player -> player.addDistrictCards(deckDistrictCards.getCard(3)));
    }



    public List<Card> getDistrictCards(int numberOfCards){
        return deckDistrictCards.getCard(numberOfCards);
    }


    public void getRandomCharacter(){
        Collections.shuffle(playerChooseOrder);

        for (Player player : playerChooseOrder) {
            for (int j = 0; j < 2; j++) {
                player.addCharacterCard((CharacterCard) deckCharacterCards.getRandomCard());
            }
        }
    }

    public void skipCharacterTurn(CharacterCard characterCard) {

    }







    /*public void passTurn(CharacterCard characterCard){
        if (turns.contains(characterCard))
    }*/

}
