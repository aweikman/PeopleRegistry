package springboot.repository;

import springboot.model.Sessions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionsRepository extends JpaRepository <Sessions, String> {
}