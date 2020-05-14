package pl.agh.customers.common.response;

import lombok.Data;
import pl.agh.customers.mysql.entity.User;
import pl.agh.customers.mysql.entity.UserRoles;
import pl.agh.customers.mysql.enums.RoleEnum;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserResponse {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private Boolean enabled;
    private Long lastShoppingCardId;
    private Set<String> roles;

    public UserResponse(User user) {
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.enabled = user.getEnabled();
        this.lastShoppingCardId = user.getLastShoppingCardId();
        this.roles = user.getRoles() == null ? new HashSet<>() :
                user.getRoles().stream()
                        .map(UserRoles::getRoleEnum)
                        .map(RoleEnum::name)
                        .collect(Collectors.toSet());
    }
}
