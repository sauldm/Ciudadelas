package org.saul.ciudadelas.out.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.saul.ciudadelas.out.entity.PlayerEntity;

import java.util.Optional;

public interface PlayerJpaRepository extends JpaRepository<PlayerEntity, Long> {
    Optional<PlayerEntity> findByNickName(String name);
}
