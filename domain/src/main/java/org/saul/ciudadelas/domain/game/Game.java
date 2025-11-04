package org.saul.ciudadelas.domain.game;

import org.saul.ciudadelas.domain.exception.InternalGameException;
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
    private List<Round> rounds;


    public Game(DeckCards deckDistrictCards, List<Player> players, DeckCards deckCharacterCards) {
        this.deckDistrictCards = deckDistrictCards;
        this.players = players;
        this.deckCharacterCards = deckCharacterCards;
        this.rounds = new ArrayList<>();

        this.players.forEach(player -> player.addDistrictCards(deckDistrictCards.getCard(3)));
    }


    public void addNewRound(){
        List<Card> orderedCharacters = deckCharacterCards.orderCards();
        Round round = new Round(orderedCharacters);
        rounds.add(round);
    }

    public List<Card> getDistrictCards(int numberOfCards){
        return deckDistrictCards.getCard(numberOfCards);
    }


    public void addRandomCharacter(){
        for (Player player : players) {
            for (int j = 0; j < 2; j++) {
                player.addCharacterCard((CharacterCard) deckCharacterCards.getRandomCard());
            }
        }
    }

    public void skipCharacterTurn(CharacterCard characterCard) {

    }



