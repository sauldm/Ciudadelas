package org.saul.ciudadelas.domain.game.deck_cards;

import org.saul.ciudadelas.domain.exception.ExpectedGameError;
import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.deck_cards.cards.Card;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class DeckCards {
    private final List<Card> cards = new ArrayList<>();
    private final Random random = new Random();



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

    public void addCard(Card card){
        if (card == null) throw new InternalGameException("La carta no puede ser nula");
        cards.add(card);
    }


    public Card getRandomCard(){
        if (cards.isEmpty()) throw new InternalGameException("No hay cartas disponibles");
        int randomIndex = random.nextInt(cards.size());
        return cards.remove(randomIndex);
    }

    public List<Card> orderCards(){
        return cards.stream().sorted().toList();

    }


    @Override
    public String toString() {
        return "DeckCards{" +
                "cards=" + cards +
                '}';
    }

    public boolean haveThisCard(CharacterCard characterCard) {
        return cards.stream().anyMatch(card -> card.getClass().equals(characterCard.getClass()));
    }
}
