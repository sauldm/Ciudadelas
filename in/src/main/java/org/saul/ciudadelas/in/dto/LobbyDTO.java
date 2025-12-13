package org.saul.ciudadelas.in.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class LobbyDTO {
    private UUID id;
    private List<PlayerDTO> players;
}
