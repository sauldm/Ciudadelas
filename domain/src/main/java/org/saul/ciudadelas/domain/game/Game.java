package org.saul.ciudadelas.domain.game;

import org.saul.ciudadelas.domain.game.deck_cards.cards.Card;
import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;
import org.saul.ciudadelas.domain.game.players.Player;

import java.util.List;

public class Game {
    private final DeckCards deckDistrictCards;
    private List<Player> players;
    private DeckCards deckCharacterCards;


    public Game(DeckCards deckCards){
        this.deckDistrictCards = deckCards;
    }

    public List<Card> getDistrictCards(int cards){
        return deckDistrictCards.getCard(cards);
    }

    public void endGame(){

    }

}
