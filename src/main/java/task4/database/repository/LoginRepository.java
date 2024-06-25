package task4.database.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import task4.database.model.Login;

@Repository
public interface LoginRepository extends ListCrudRepository<Login, Integer> {

}
