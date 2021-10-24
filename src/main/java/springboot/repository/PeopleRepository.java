package springboot.repository;

import springboot.model.People;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleRepository extends JpaRepository <People, Integer>  {
}