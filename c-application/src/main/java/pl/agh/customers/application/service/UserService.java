package pl.agh.customers.application.service;

import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import pl.agh.customers.application.dto.UserPostRequestDTO;
import pl.agh.customers.application.dto.UserPutRequestDTO;
import pl.agh.customers.application.rest.MicroService;
import pl.agh.customers.application.rest.RestClient;
import pl.agh.customers.common.exception.BadRequestException;
import pl.agh.customers.common.exception.NotFoundException;
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
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRolesRepository userRolesRepository;
    private final RestClient restClient;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserResponse create(UserPostRequestDTO userDTO) throws BadRequestException, NotFoundException {
        if (userRepository.existsById(userDTO.getUsername())) {
            throw new BadRequestException("Username is used");
        }
        if (userDTO.getLastShoppingCardId() != null) {
            updateShoppingCart(userDTO.getUsername(), userDTO.getLastShoppingCardId());
        }
        User user = userDTO.toEntity();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
        Collections.sort(userList);
        int count = userList.size();
        userList = ListUtil.clampedSublist(userList, limit, offset);
        List<UserResponse> userResponseList = mapUserListToUserResponseList(userList);
        return new ListResponse(userResponseList, count);
    }

    public UserResponse find(String username) {
        Optional<User> userOpt = userRepository.findById(username);
        return userOpt.map(UserResponse::new).orElse(null);
    }

    public UserResponse update(String username, UserPutRequestDTO userDTO) throws NotFoundException, BadRequestException {
        Optional<User> userOpt = userRepository.findById(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (userDTO.getLastShoppingCardId() != null) {
                updateShoppingCart(user.getUsername(), userDTO.getLastShoppingCardId());
            }
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setPhone(userDTO.getPhone());
            user.setAddress(userDTO.getAddress());
            user.setEnabled(userDTO.getEnabled());
            user.setLastShoppingCardId(userDTO.getLastShoppingCardId());
            user = userRepository.save(user);
            return new UserResponse(user);
        }
        return null;
    }

    public User updatePassword(String username, String newPassword) {
        Optional<User> userOpt = userRepository.findById(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            return userRepository.save(user);
        }
        return null;
    }

    public User delete(String username) {
        Optional<User> user = userRepository.findById(username);
        if (user.isEmpty()) {
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

    public User getUser(String username) {
        return userRepository.findById(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("Username '%s' not found", username)));

    }

    private void updateShoppingCart(String username, Long shoppingCardId) throws NotFoundException, BadRequestException {
        JSONObject request = new JSONObject();
        request.put("username", username);
        try {
            restClient.put(MicroService.CART_MS, "/shoppingCards/" + shoppingCardId, request);
        } catch (HttpClientErrorException.NotFound e) {
            throw new NotFoundException("shopping cart does not exist");
        } catch (HttpClientErrorException.Forbidden e) {
            throw new BadRequestException("shopping cart has different owner");
        }
    }

}
