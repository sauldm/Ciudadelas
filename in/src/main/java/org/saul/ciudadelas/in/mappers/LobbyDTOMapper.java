package org.saul.ciudadelas.in.mappers;

import org.saul.ciudadelas.domain.game.deck_cards.cards.Card;
import org.saul.ciudadelas.domain.game.players.Player;
import org.saul.ciudadelas.domain.lobby.Lobby;
import org.saul.ciudadelas.in.dto.CardDTO;
import org.saul.ciudadelas.in.dto.LobbyDTO;

import java.util.ArrayList;
import java.util.List;

public class LobbyDTOMapper {
    public static LobbyDTO toLobbyDTO(Lobby lobby){
        LobbyDTO lobbyDTO = new LobbyDTO();
        lobbyDTO.setId(lobby.getId());
        if (lobbyDTO.getPlayers() != null)
            lobbyDTO.setPlayers(lobby.getPlayers().stream().map(Player::getNickName).toList());
        return lobbyDTO;
    }

    public static List<LobbyDTO> toLobbyDTOList(List<Lobby> lobbys) {
        List<LobbyDTO> dtos = new ArrayList<>();
        for (Lobby lobby : lobbys) {
            dtos.add(toLobbyDTO(lobby));
        }
        return dtos;
    }
}
