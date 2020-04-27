package pl.agh.customers.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPutRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private Boolean enabled;
}