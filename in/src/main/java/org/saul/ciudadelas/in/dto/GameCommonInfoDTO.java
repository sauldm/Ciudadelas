package org.saul.ciudadelas.in.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class GameCommonInfoDTO {
    private UUID id;
    private Long characterTurnId;
    private Long characterSkipped;
    private Long characterRobbed;
    private boolean turnCompleted;
    private boolean characterHabilityUsed;
    private List<Long> districtsHabilityUsed;

    private List<PlayerCommonInfoDTO> playerCommonInfoDTOS;


}
