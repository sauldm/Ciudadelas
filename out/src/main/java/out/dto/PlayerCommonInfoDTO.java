package out.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlayerCommonInfoDTO {
    private Long gold;
    private List<CardDTO> districtsBuilt;
    private List<CardDTO> characterCardsPlayed;
    private int districtsInHand;
}
