package pl.agh.customers.application.service;

import org.springframework.stereotype.Service;
import pl.agh.customers.common.exception.BadRequestException;
import pl.agh.customers.common.exception.CustomException;
import pl.agh.customers.common.exception.NotFoundException;
import pl.agh.customers.common.response.UserRolesResponse;
import pl.agh.customers.mysql.entity.User;
import pl.agh.customers.mysql.entity.UserRoles;
import pl.agh.customers.mysql.enums.RoleEnum;
import pl.agh.customers.mysql.repository.UserRepository;
import pl.agh.customers.mysql.repository.UserRolesRepository;

import java.util.Optional;

@Service
public class UserRolesService {

    private final UserRepository userRepository;
    private final UserRolesRepository userRolesRepository;

    public UserRolesService(UserRepository userRepository, UserRolesRepository userRolesRepository) {
        this.userRepository = userRepository;
        this.userRolesRepository = userRolesRepository;
    }

    public UserRolesResponse addUserRoleAssociation(String username, String roleEnumString) throws CustomException {
        User user = userRepository.findById(username).orElseThrow(() -> new NotFoundException("User does not exist"));
        RoleEnum roleEnum =
                Optional.ofNullable(RoleEnum.fromValue(roleEnumString)).orElseThrow(() -> new NotFoundException("Role does " +
                        "not exist"));

        if (userRolesRepository.existsByUserAndRoleEnum(user, roleEnum)) {
            throw new BadRequestException("User role association already exists");
        }

        UserRoles userRoles = userRolesRepository.save(new UserRoles(user, roleEnum));
        return new UserRolesResponse(userRoles);
    }

    public void deleteUserRoleAssociation(String username, String roleEnumString) throws CustomException {
        User user = userRepository.findById(username).orElseThrow(() -> new NotFoundException("User does not exist"));
        RoleEnum roleEnum =
                Optional.ofNullable(RoleEnum.fromValue(roleEnumString)).orElseThrow(() -> new NotFoundException("Role does " +
                        "not exist"));

        UserRoles userRoles =
                userRolesRepository.findByUserAndRoleEnum(user, roleEnum).orElseThrow(() -> new NotFoundException(
                        "User role association does not exist"));

        userRolesRepository.delete(userRoles);
    }
}
