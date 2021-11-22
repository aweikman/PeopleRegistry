package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springboot.model.AuditTrail;
import springboot.model.Users;

import java.util.List;

public interface AuditTrailRepository extends JpaRepository<AuditTrail, Integer> {
    List<AuditTrail> findAllByPersonId(int person_id);
}