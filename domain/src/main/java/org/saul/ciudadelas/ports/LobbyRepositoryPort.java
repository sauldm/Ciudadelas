package org.saul.ciudadelas.ports;

import org.saul.ciudadelas.domain.lobby.Lobby;

import java.util.Optional;


public interface LobbyRepositoryPort {

    Optional<Lobby> findById(Long id);
    Lobby save(Lobby lobby);


}
