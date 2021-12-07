package springboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import springboot.model.People;

public interface PeopleRepository extends JpaRepository <People, Integer>  {
    Page<People> findAllByLastNameStartingWith(String lastName, Pageable pageable);
}