package org.saul.ciudadelas.domain.game.deck_cards;

import org.saul.ciudadelas.domain.exception.ExpectedGameError;
import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.deck_cards.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class DeckCards {
    private final List<Card> cards = new ArrayList<>();


    public List<Card> getCard(int quantity){
        if (cards.isEmpty())  throw new ExpectedGameError("No hay cartas en el mazo");
        if (cards.size() < quantity){
            List<Card> result = new ArrayList<>(cards);
            cards.clear();
            return result;
        }

        List<Card> stoledCards = cards.subList(0,quantity);
        List<Card> result = new ArrayList<>(stoledCards);
        stoledCards.clear();

        return result;
    }

    public void addCards(List<Card> cards){
        if (cards == null) throw new InternalGameException("Las cartas no pueden ser nulas");
        cards.forEach(this.cards::addLast);
    }

    @Override
    public String toString() {
        return "DeckCards{" +
                "cards=" + cards.size() +
                '}';
    }
}
