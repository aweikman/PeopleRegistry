package springboot.repository;

import springboot.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository <Users, Integer> {

//    List<Users> findAllByLoginAndPAssword(String username, String hashedPassword);
}