package out.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import out.entity.LobbyEntity;

import java.util.UUID;

public interface LobbyJpaRepository extends JpaRepository<LobbyEntity, UUID> {
}
