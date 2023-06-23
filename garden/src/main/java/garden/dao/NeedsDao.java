package garden.dao;

import garden.entity.Needs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NeedsDao extends JpaRepository<Needs, Long> {
}
