package xavr.todolist.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import xavr.todolist.domain.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();
    Optional<User> findByEmailAndPassword(String email, String username);



//    @Query(value = "INSERT INTO _USER (EMAIL, PASSWORD) VALUES ('Bob@bskom.az', 'Getout')")
//    String INSERT_TEST_QUERY();
}
