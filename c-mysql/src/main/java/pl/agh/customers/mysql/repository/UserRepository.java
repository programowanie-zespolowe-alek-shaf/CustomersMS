package pl.agh.customers.mysql.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.agh.customers.mysql.entity.User;

import java.util.List;

@Transactional
@Repository
public interface UserRepository extends CrudRepository<User, String> {

    List<User> findAll();
}
