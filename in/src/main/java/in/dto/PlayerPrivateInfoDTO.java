package in.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlayerPrivateInfoDTO {

    private List<CardDTO> districtsInHand;
    private List<CardDTO> characterCardsNotPlayed;
}
