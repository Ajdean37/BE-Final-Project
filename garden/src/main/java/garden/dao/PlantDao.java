package garden.dao;

import garden.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantDao extends JpaRepository<Plant, Long> {
}
