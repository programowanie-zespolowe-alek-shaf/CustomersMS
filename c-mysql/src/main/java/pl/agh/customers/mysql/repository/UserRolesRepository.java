package pl.agh.customers.mysql.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.agh.customers.mysql.entity.UserRoles;

@Transactional
@Repository
public interface UserRolesRepository extends CrudRepository<UserRoles, Long> {
}
