package pl.agh.customers.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.agh.customers.mysql.entity.User;

import java.util.HashSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPostRequestDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private Boolean enabled;

    public User toEntity() {
        return new User(username, password, firstName, lastName, email, phone, address, enabled, new HashSet<>());
    }
}
