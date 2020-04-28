package pl.agh.customers.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.agh.customers.application.dto.UserPostRequestDTO;
import pl.agh.customers.application.dto.UserPutRequestDTO;
import pl.agh.customers.common.exception.BadRequestException;
import pl.agh.customers.common.response.ListResponse;
import pl.agh.customers.common.response.UserResponse;
import pl.agh.customers.common.util.ListUtil;
import pl.agh.customers.mysql.entity.User;
import pl.agh.customers.mysql.entity.UserRoles;
import pl.agh.customers.mysql.enums.RoleEnum;
import pl.agh.customers.mysql.repository.UserRepository;
import pl.agh.customers.mysql.repository.UserRolesRepository;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRolesRepository userRolesRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserRolesRepository userRolesRepository) {
        this.userRepository = userRepository;
        this.userRolesRepository = userRolesRepository;
    }

    public UserResponse create(UserPostRequestDTO userDTO) throws BadRequestException {
        if (userRepository.existsById(userDTO.getUsername())) {
            throw new BadRequestException("Username is used");
        }
        User user = userDTO.toEntity();
        user = userRepository.save(user);
        UserRoles userRole = new UserRoles(user, RoleEnum.ROLE_USER);
        userRole = userRolesRepository.save(userRole);

        Set<UserRoles> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRoles(userRoles);
        return new UserResponse(user);
    }

    public ListResponse findUsers(int limit, int offset) {
        List<User> userList = userRepository.findAll();
        int count = userList.size();
        userList = ListUtil.clampedSublist(userList, limit, offset);
        List<UserResponse> userResponseList = mapUserListToUserResponseList(userList);
        return new ListResponse(userResponseList, count);
    }

    public UserResponse find(String username) {
        User user = userRepository.findById(username).orElse(null);
        if (user == null) {
            return null;
        }
        return new UserResponse(user);
    }

    public UserResponse update(String username, UserPutRequestDTO userDTO) throws BadRequestException {
        User user = userRepository.findById(username).orElseThrow(() -> new BadRequestException("User does not exist"));

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setEnabled(userDTO.getEnabled());

        user = userRepository.save(user);
        return new UserResponse(user);
    }

    public void updatePassword(String username, String newPassword) throws BadRequestException {
        User user = userRepository.findById(username).orElseThrow(() -> new BadRequestException("User does not exist"));

        user.setPassword(newPassword);
        userRepository.save(user);
    }

    public User delete(String username) {
        Optional<User> user = userRepository.findById(username);
        if (!user.isPresent()) {
            return null;
        }
        userRepository.delete(user.get());
        return user.get();
    }

    private List<UserResponse> mapUserListToUserResponseList(List<User> userList) {
        List<UserResponse> userResponseList = new ArrayList<>();
        for (User u : userList) {
            userResponseList.add(new UserResponse(u));
        }
        return userResponseList;
    }
}
