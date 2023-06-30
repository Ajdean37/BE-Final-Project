package garden.dao;

import garden.entity.Needs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface NeedsDao extends JpaRepository<Needs, Long> {
 Set<Needs> findAllByNeedsIn(Set<String> need);
}
