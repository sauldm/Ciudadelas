package org.saul.ciudadelas.domain.game;

import org.saul.ciudadelas.domain.exception.ExpectedGameError;
import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.deck_cards.actions.KingActionCard;
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
    public List<Player> playerChooseOrder;
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

    public void getCharacterOrder(){
        KingActionCard kingActionCard = new KingActionCard();
        int indexKingCard = -1;
        int searchKingError = 0;

        for (int i = 0; i < playerChooseOrder.size(); i++) {
            if (playerChooseOrder.get(i).haveThisCard(kingActionCard)){
                indexKingCard = i;
                searchKingError++;
            }
        }
        if (searchKingError == 0) throw new InternalGameException("No puede no haber carta de rey");
        if (searchKingError >=2) throw new InternalGameException("No puede haber mas de una carta de rey");

        System.out.println(indexKingCard);
        List<Player> auxPlayers = new ArrayList<>(playerChooseOrder.subList(indexKingCard,playerChooseOrder.size()));
        auxPlayers.addAll(playerChooseOrder.subList(0,indexKingCard));

        playerChooseOrder = auxPlayers;
    }

    private void nextPlayerSelecting() {
        if (indexPlayerSelecting >= players.size()) indexPlayerSelecting = 0;
        indexPlayerSelecting++;
    }

    /*public void passTurn(CharacterCard characterCard){
        if (turns.contains(characterCard))
    }*/

}
