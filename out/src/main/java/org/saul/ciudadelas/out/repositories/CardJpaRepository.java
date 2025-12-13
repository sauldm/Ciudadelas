package org.saul.ciudadelas.out.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.saul.ciudadelas.out.entity.DistrictCardEntity;

public interface CardJpaRepository extends JpaRepository<DistrictCardEntity,Long> {
}
