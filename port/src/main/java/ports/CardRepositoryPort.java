package ports;

import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;
import org.saul.ciudadelas.domain.game.deck_cards.cards.DistrictCard;

import java.util.List;

public interface CardRepositoryPort {
    DeckCards<DistrictCard> findAllCards();
}
