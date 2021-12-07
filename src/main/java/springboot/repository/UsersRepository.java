package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import springboot.model.Users;

import java.util.List;

public interface UsersRepository extends JpaRepository <Users, Integer> {
    //Query to find user by name/pass
    List <Users> findUserByUsernameAndPassword(String username, String password);

    // Manual named query to find id by name
    @Query("SELECT id FROM Users WHERE login=:username")
    List <Integer> findIDByUsername(@Param("username") String username);
}