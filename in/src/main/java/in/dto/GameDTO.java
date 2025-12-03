package in.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class GameDTO {
    private UUID id;
    private List<PlayerDTO> players;

}
