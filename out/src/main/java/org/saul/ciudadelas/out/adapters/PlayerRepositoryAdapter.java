package org.saul.ciudadelas.out.adapters;

import org.saul.ciudadelas.domain.game.PlayerClassification;
import org.saul.ciudadelas.domain.game.players.Player;
import org.saul.ciudadelas.out.projection.PlayerClassificationP;
import org.saul.ciudadelas.ports.PlayerRepositoryPort;
import org.springframework.stereotype.Repository;
import org.saul.ciudadelas.out.entity.PlayerEntity;
import org.saul.ciudadelas.out.mapper.PlayerMapper;
import org.saul.ciudadelas.out.repositories.PlayerJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Transactional

    @Override
    public Player save(Player player) {
        PlayerEntity entity = mapper.toEntity(player);
        PlayerEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }


    @Override
    public List<PlayerClassification> findAllByOrderByGamesWonDesc() {
        List<PlayerClassification> classifications = new ArrayList<>();

        for (PlayerClassificationP playerClassification:jpaRepository.findAllByOrderByWinsDesc()){
            classifications.add(new PlayerClassification(playerClassification.nickName(), playerClassification.wins()));
        }
        return classifications;
    }
}
