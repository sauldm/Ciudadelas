package out.dto;

import org.saul.ciudadelas.domain.game.deck_cards.DeckCards;

import java.util.List;

public class PlayerCommonInfoDTO {
    private Long gold;
    private List<CardDTO> districtsBuilt;
    private List<CardDTO> characterCardsPlayed;
    private int districtsInHand;
}
