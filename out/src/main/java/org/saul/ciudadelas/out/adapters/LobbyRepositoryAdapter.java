package org.saul.ciudadelas.out.adapters;

import org.saul.ciudadelas.domain.lobby.Lobby;
import org.saul.ciudadelas.ports.LobbyRepositoryPort;
import org.springframework.stereotype.Repository;
import org.saul.ciudadelas.out.entity.LobbyEntity;
import org.saul.ciudadelas.out.mapper.LobbyMapper;
import org.saul.ciudadelas.out.repositories.LobbyJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class LobbyRepositoryAdapter implements LobbyRepositoryPort {

    private final LobbyJpaRepository jpaRepository;
    private final LobbyMapper mapper;

    public LobbyRepositoryAdapter(LobbyJpaRepository jpaRepository, LobbyMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }


    @Override
    public Optional<Lobby> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Transactional
    @Override
    public Lobby save(Lobby lobby) {
        LobbyEntity entity = mapper.toEntity(lobby);
        jpaRepository.save(entity);
        return mapper.toDomain(entity);
    }

    @Transactional

    @Override
    public Lobby remove(UUID id) {
        LobbyEntity entity = mapper.toEntity(findById(id).orElseThrow(() -> new RuntimeException("El lobby no existe")));
        jpaRepository.delete(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public List<UUID> findAllLobbyWithMaxTwoPlayers() {
        return jpaRepository.findAllLobbyWithMaxTwoPlayers();
    }

    @Override
    public List<Lobby> findAllLobbys() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<String> findAllPlayers(UUID id) {
        return jpaRepository.findPlayerNickNames(id);

    }

    @Override
    @Transactional
    public boolean removePlayer(UUID lobbyId, String nickName) {
        return jpaRepository.removePlayer(lobbyId, nickName) > 0;
    }
}
