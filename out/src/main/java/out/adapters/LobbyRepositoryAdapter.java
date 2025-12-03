package out.adapters;

import org.saul.ciudadelas.domain.lobby.Lobby;
import org.saul.ciudadelas.ports.LobbyRepositoryPort;
import org.springframework.stereotype.Repository;
import out.mapper.LobbyMapper;
import out.repositories.LobbyJpaRepository;

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
        return Optional.empty();
    }

    @Override
    public Lobby save(Lobby lobby) {
        return null;
    }

    @Override
    public Lobby remove(UUID id) {
        return null;
    }
}
