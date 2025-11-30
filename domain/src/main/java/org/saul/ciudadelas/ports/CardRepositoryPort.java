package org.saul.ciudadelas.ports;

import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;
import org.saul.ciudadelas.domain.game.deck_cards.cards.DistrictCard;

public interface CardRepositoryPort {
    DeckCards<DistrictCard> findAllCards();
}
