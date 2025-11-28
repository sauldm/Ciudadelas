package out.respositories;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.saul.ciudadelas.domain.game.Round;
import org.saul.ciudadelas.domain.game.RoundEvent;
import org.saul.ciudadelas.domain.game.Turn;
import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;
import org.saul.ciudadelas.domain.game.deck_cards.cards.CharacterCard;
import org.saul.ciudadelas.domain.game.deck_cards.cards.DistrictCard;
import org.saul.ciudadelas.domain.game.players.Player;

import java.util.List;

@Entity
@Setter
@Getter
public class GameMO {
    @Id
    @GeneratedValue
    private Long id;

    private DeckCards<DistrictCard> deckDistrictCards;
    private List<Player> players;
    private DeckCards<CharacterCard> deckCharacterCards;
    private List<Round> rounds;
    private List<RoundEvent> specialRoundEvents;
    private Turn turnSkipped;
}
