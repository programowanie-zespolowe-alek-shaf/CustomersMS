package pl.agh.customers.common.response;

import lombok.Data;
import pl.agh.customers.mysql.entity.UserRoles;
import pl.agh.customers.mysql.enums.RoleEnum;

@Data
public class UserRolesResponse {

    private Long id;
    private String username;
    private RoleEnum roleEnum;

    public UserRolesResponse(UserRoles userRoles) {
        this.id = userRoles.getId();
        this.username = userRoles.getUser().getUsername();
        this.roleEnum = userRoles.getRoleEnum();
    }
}
