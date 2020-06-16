package pl.agh.customers.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.agh.customers.application.dto.UserPostRequestDTO;
import pl.agh.customers.application.dto.UserPutRequestDTO;
import pl.agh.customers.application.service.UserService;
import pl.agh.customers.application.service.ValidationService;
import pl.agh.customers.common.exception.CustomException;
import pl.agh.customers.common.response.ListResponse;
import pl.agh.customers.common.response.UserResponse;
import pl.agh.customers.mysql.entity.User;

import java.net.URI;

import static pl.agh.customers.common.util.ResponseFormat.APPLICATION_JSON;

@RestController
@RequestMapping(value = UserController.PREFIX)
@RequiredArgsConstructor
public class UserController {

    static final String PREFIX = "/users";

    private final UserService userService;

    private final ValidationService validationService;

    @PostMapping(consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ResponseEntity<UserResponse> createUser(@RequestBody UserPostRequestDTO userDTO) throws CustomException {
        validationService.validate(userDTO);
        UserResponse createdUser = userService.create(userDTO);
        if (createdUser == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{username}")
                    .buildAndExpand(createdUser.getUsername())
                    .toUri();

            return ResponseEntity.created(uri)
                    .body(createdUser);
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(produces = APPLICATION_JSON)
    public ResponseEntity<ListResponse> getAllUsers(@RequestParam int limit,
                                                    @RequestParam int offset) throws CustomException {
        validationService.validate(limit, offset);
        ListResponse users = userService.findUsers(limit, offset);
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("#username == principal or hasRole('ROLE_ADMIN')")
    @GetMapping(value = "{username}", produces = APPLICATION_JSON)
    public ResponseEntity<UserResponse> getUser(@PathVariable("username") String username) {
        UserResponse user = userService.find(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @PreAuthorize("#username == principal or hasRole('ROLE_ADMIN')")
    @PutMapping(value = "{username}", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ResponseEntity<UserResponse> updateUser(@PathVariable("username") String username,
                                                   @RequestBody UserPutRequestDTO userDTO) throws CustomException {
        validationService.validate(userDTO);
        UserResponse user = userService.update(username, userDTO);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }

    @PreAuthorize("#username == principal or hasRole('ROLE_ADMIN')")
    @PatchMapping(value = "{username}", produces = APPLICATION_JSON)
    public ResponseEntity<Object> updateUserPassword(@PathVariable("username") String username,
                                                     @RequestParam String newPassword) {
        User user = userService.updatePassword(username, newPassword);
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "{username}", produces = APPLICATION_JSON)
    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
        User deletedUser = userService.delete(username);
        if (deletedUser == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
