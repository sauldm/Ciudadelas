package org.saul.ciudadelas.ports;

import org.saul.ciudadelas.domain.lobby.Lobby;

import java.util.Optional;
import java.util.UUID;


public interface LobbyRepositoryPort {

    Optional<Lobby> findById(UUID id);
    Lobby save(Lobby lobby);
    Lobby remove(UUID id);


}
