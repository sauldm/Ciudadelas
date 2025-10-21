package org.saul.ciudadelas.domain.game;

import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.deck_cards.cards.Card;
import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;
import org.saul.ciudadelas.domain.game.players.Player;

import java.util.List;

public class Game {
    private final DeckCards deckDistrictCards;
    private List<Player> players;
    private DeckCards deckCharacterCards;


    public Game(DeckCards deckDistrictCards, List<Player> players, DeckCards deckCharacterCards) {
        this.deckDistrictCards = deckDistrictCards;
        this.players = players;
        this.deckCharacterCards = deckCharacterCards;
        this.players.forEach(player -> player.addDistrictCards(deckDistrictCards.getCard(3)));
    }

    public List<Card> getDistrictCards(int cards){
        return deckDistrictCards.getCard(cards);
    }


    public void endGame(){

    }

    public void startGame(){

    }
}
