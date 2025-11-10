package org.saul.ciudadelas.domain.game.deck_cards;

import org.saul.ciudadelas.domain.exception.ExpectedGameError;
import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.deck_cards.cards.Card;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class DeckCards<T extends Card> {
    private final List<T> cards = new ArrayList<>();
    private final Random random = new Random();



    public List<T> getCard(int quantity){
        if (cards.isEmpty())  throw new ExpectedGameError("No hay cartas en el mazo");
        if (cards.size() < quantity){
            List<T> result = new ArrayList<>(cards);
            cards.clear();
            return result;
        }

        List<T> stoledCards = cards.subList(0,quantity);
        List<T> result = new ArrayList<>(stoledCards);
        stoledCards.clear();

        return result;
    }

    public T getCard(T card){
        if (card == null) throw new InternalGameException("card no puede ser null");
        if (!cards.contains(card)) throw new InternalGameException("La carta tiene que estar en el mazo");
        cards.remove(card);
        return card;
    }

    public void addCards(List<T> cards){
        if (cards == null) throw new InternalGameException("Las cartas no pueden ser nulas");
        cards.forEach(this.cards::addLast);
    }

    public void addCard(T card){
        if (card == null) throw new InternalGameException("La carta no puede ser nula");
        cards.add(card);
    }


    public T getRandomCard(){
        if (cards.isEmpty()) throw new InternalGameException("No hay cartas disponibles");
        int randomIndex = random.nextInt(cards.size());
        return cards.remove(randomIndex);
    }

    public List<T> orderCards(){
        return cards.stream().sorted().toList();

    }


    @Override
    public String toString() {
        return "DeckCards{" +
                "cards=" + cards +
                '}';
    }

    public boolean haveThisCard(T card) {
        return cards.stream().anyMatch(currentCard -> currentCard.getClass().equals(card.getClass()));
    }
}
