package in.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlayerCommonInfoDTO {
    private String nickName;
    private Long gold;
    private List<CardDTO> districtsBuilt;
    private List<CardDTO> characterCardsPlayed;
    private int numberDistrictsInHand;
}
