package org.saul.ciudadelas.out.repositories;

import org.saul.ciudadelas.out.projection.PlayerClassificationP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.saul.ciudadelas.out.entity.PlayerEntity;

import java.util.List;
import java.util.Optional;

public interface PlayerJpaRepository extends JpaRepository<PlayerEntity, Long> {
    Optional<PlayerEntity> findByNickName(String name);

    List<PlayerClassificationP> findAllByOrderByWinsDesc();

}
