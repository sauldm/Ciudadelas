package org.saul.ciudadelas.domain.game;

import org.saul.ciudadelas.domain.exception.ExpectedGameError;
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


    public void addNewRound() {
        List<Card> orderedCharacters = deckCharacterCards.orderCards();
        Round round = new Round(orderedCharacters);
        rounds.add(round);
    }

    public List<Card> getDistrictCards(int numberOfCards) {
        return deckDistrictCards.getCard(numberOfCards);
    }


    public void addRandomCharacter() {
        for (Player player : players) {
            for (int j = 0; j < 1; j++) {
                player.addCharacterCard((CharacterCard) deckCharacterCards.getRandomCard());
            }
        }
    }

    public void skipCharacterTurn(CharacterCard characterCard) {

    }

    public void stoleCharacterGold(CharacterCard characterRobed,CharacterCard characterThief) {
        if (characterRobed == null) throw new InternalGameException("La carta no puede ser nula");
        if (characterThief == null) throw new InternalGameException("La carta no puede ser nula");
        Player playerThief = getPlayerByCharacter(characterThief);
        if (playerThief == null) throw new InternalGameException("El jugador que roba no puede ser nulo");
        Player playerRobed = getPlayerByCharacter(characterRobed);
        if (playerRobed == null) return;

        playerThief.addGold(playerRobed.getAllGold());

    }

    public Player getPlayerByCharacter(CharacterCard characterCard){
        if (characterCard == null) throw new InternalGameException("La carta no puede ser nula");
        for (Player player: players) {
            if (player.haveCharacter(characterCard)) return player;
        }
        return null;
    }

    private Round getActualRound(){
        return rounds.getLast();
    }
}



