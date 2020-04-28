package pl.agh.customers.mysql.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.agh.customers.mysql.entity.User;
import pl.agh.customers.mysql.entity.UserRoles;
import pl.agh.customers.mysql.enums.RoleEnum;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserRolesRepository extends CrudRepository<UserRoles, Long> {

    List<UserRoles> findByUser(User user);

    boolean existsByUserAndRoleEnum(User user, RoleEnum roleEnum);

    Optional<UserRoles> findByUserAndRoleEnum (User user, RoleEnum roleEnum);
}
