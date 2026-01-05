package org.saul.ciudadelas.in.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PlayerDTO {
    private Long id;
    private String nickName;
    private int wins;
}
