package out.adapters;

import org.saul.ciudadelas.domain.game.players.Player;
import org.saul.ciudadelas.domain.lobby.Lobby;
import org.saul.ciudadelas.ports.LobbyRepositoryPort;
import org.springframework.stereotype.Repository;
import out.entity.LobbyEntity;
import out.entity.PlayerEntity;
import out.mapper.LobbyMapper;
import out.repositories.LobbyJpaRepository;

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

    @Override
    public Lobby save(Lobby lobby) {
        LobbyEntity entity = mapper.toEntity(lobby);
        jpaRepository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public Lobby remove(UUID id) {
        LobbyEntity entity = mapper.toEntity(findById(id).orElseThrow(() -> new RuntimeException("El lobby no existe")));
        jpaRepository.delete(entity);
        return mapper.toDomain(entity);
    }
}
