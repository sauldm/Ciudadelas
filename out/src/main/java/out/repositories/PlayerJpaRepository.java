package out.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import out.entity.PlayerEntity;

import java.util.UUID;

public interface PlayerJpaRepository extends JpaRepository<PlayerEntity, UUID> {
}
