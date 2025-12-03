package out.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import out.entity.DistrictCardEntity;

public interface CardJpaRepository extends JpaRepository<DistrictCardEntity,Long> {
}
