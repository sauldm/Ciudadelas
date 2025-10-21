package org.saul.ciudadelas.domain.game;

import org.saul.ciudadelas.domain.exception.ExpectedGameError;
import org.saul.ciudadelas.domain.game.deck_cards.cards.Card;
import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;
import org.saul.ciudadelas.domain.game.players.Player;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final DeckCards deckDistrictCards;
    private List<Player> players;
    private DeckCards deckCharacterCards;
    private int rondas;
    private List<Turn> turns;
    private List<Player> playerChooseOrder;
    private int indexPlayerSelecting;


    public Game(DeckCards deckDistrictCards, List<Player> players, DeckCards deckCharacterCards) {
        this.deckDistrictCards = deckDistrictCards;
        this.players = players;
        this.deckCharacterCards = deckCharacterCards;
        this.rondas = 1;
        this.turns = new ArrayList<>();
        this.playerChooseOrder = players;
        this.indexPlayerSelecting = 0;

        this.players.forEach(player -> player.addDistrictCards(deckDistrictCards.getCard(3)));
    }



    public List<Card> getDistrictCards(int numberOfCards){
        return deckDistrictCards.getCard(numberOfCards);
    }

    public void chooseCharacter(CharacterCard characterCard){
        playerChooseOrder.get(indexPlayerSelecting).getCharacterCard(characterCard);
        nextPlayerSelecting();
    }

    private void nextPlayerSelecting() {
        indexPlayerSelecting++;
    }

    /*public void passTurn(CharacterCard characterCard){
        if (turns.contains(characterCard))
    }*/

}
