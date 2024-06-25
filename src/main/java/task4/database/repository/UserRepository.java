package task4.database.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import task4.database.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}
