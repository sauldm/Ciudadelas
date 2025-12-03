package org.saul.ciudadelas.ports;

import org.saul.ciudadelas.domain.game.deck_cards.cards.Card;
import org.saul.ciudadelas.domain.game.deck_cards.cards.DistrictCard;

import java.util.List;

public interface CardRepositoryPort {
    List<DistrictCard> findAllCards();
}
