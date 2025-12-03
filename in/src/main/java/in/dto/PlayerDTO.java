package in.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PlayerDTO {
    private UUID id;
    private String nickName;
}
