package in.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ExecuteCharacterHabilityDTO {
    private String gameId;
    private Long characterId;
    private Long targetId;
}
