package org.saul.ciudadelas.in.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.saul.ciudadelas.domain.game.deck_cards.cards.Card;

import java.util.List;

@Getter
@Setter
@ToString
public class PlayerCommonInfoDTO {
    private Long id;
    private String nickName;
    private Long gold;
    private List<CardDTO> districtsBuilt;
    private List<CardDTO> characterCardsPlayed;
    private int numberDistrictsInHand;
}
