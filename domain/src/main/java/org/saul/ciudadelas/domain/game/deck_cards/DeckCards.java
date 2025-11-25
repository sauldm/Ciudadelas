package org.saul.ciudadelas.domain.game.deck_cards;

import org.saul.ciudadelas.domain.exception.ExpectedGameError;
import org.saul.ciudadelas.domain.exception.InternalGameException;
import org.saul.ciudadelas.domain.game.deck_cards.cards.Card;

import java.util.*;

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

    public List<T> getAllCards(){
        List<T> result = new ArrayList<>(cards);
        cards.clear();
        return result;
    }

    public void addCards(List<T> cards){
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

    public T haveThisCard(T card) {
        Optional<T> cardOptional = cards.stream()
                .filter(c -> c.equals(card))
                .findFirst();
        return cardOptional.orElse(null);
    }

    public T haveThisCard(Long cardId) {
        Optional<T> cardOptional = cards.stream()
                .filter(c -> c.getId().equals(cardId))
                .findFirst();
        return cardOptional.orElse(null);
    }

    public Long getIntSameColorCards(Color color) {
        return  cards.stream()
                .filter(card -> card.getColor().equals(color))
                .count();
    }

    public List<T> findCardWithInstance(Class<StartTurnEpicCard> startTurnEpicCardClass) {
        List<T> result = new ArrayList<>();
        for (T card : cards) {
            if (startTurnEpicCardClass.isInstance(card)) {
                result.add(card);
            }
        }
        return result;
    }
}
