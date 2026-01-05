package org.saul.ciudadelas.ports;

import org.saul.ciudadelas.domain.lobby.Lobby;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface LobbyRepositoryPort {


    Optional<Lobby> findById(UUID id);
    Lobby save(Lobby lobby);
    Lobby remove(UUID id);

    List<UUID> findAllLobbyWithMaxTwoPlayers();
    List<Lobby> findAllLobbys();

    List<String> findAllPlayers(UUID id);

    boolean removePlayer(UUID lobbyId, String nickName);

    void removeLobbyWithoutPlayers();
}
