package out.adapters;

import org.saul.ciudadelas.domain.game.players.Player;
import org.saul.ciudadelas.ports.PlayerRepositoryPort;
import org.springframework.stereotype.Repository;
import out.entity.PlayerEntity;
import out.mapper.PlayerMapper;
import out.repositories.PlayerJpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class PlayerRepositoryAdapter implements PlayerRepositoryPort {

    private final PlayerJpaRepository jpaRepository;
    private final PlayerMapper mapper;

    public PlayerRepositoryAdapter(PlayerJpaRepository jpaRepository, PlayerMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }



    @Override
    public Optional<Player> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Player> findAllPlayers() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Player> findByName(String name) {
        return jpaRepository.findByNickName(name)
                .map(mapper::toDomain);

    }

    @Override
    public Player save(String nickName) {
        PlayerEntity entity = mapper.toEntity(nickName);
        return mapper.toDomain(jpaRepository.save(entity));
    }
}
