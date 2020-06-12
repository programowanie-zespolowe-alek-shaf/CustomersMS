package pl.agh.customers.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.agh.customers.application.service.UserRolesService;
import pl.agh.customers.common.exception.CustomException;
import pl.agh.customers.common.response.UserRolesResponse;

import static pl.agh.customers.common.util.ResponseFormat.APPLICATION_JSON;

@RestController
@Secured("ROLE_ADMIN")
public class UserRolesController {


    private final UserRolesService userRolesService;

    @Autowired
    public UserRolesController(UserRolesService userRolesService) {
        this.userRolesService = userRolesService;
    }

    @RequestMapping(value = "/users/{username}/roles/{roleEnum}", method = RequestMethod.POST, produces = {APPLICATION_JSON})
    public ResponseEntity<UserRolesResponse> addUserRoleAssociation(@PathVariable("username") String username,
                                                                    @PathVariable("roleEnum") String roleEnum) throws CustomException {
        UserRolesResponse createdUserRoleAssociation = userRolesService.addUserRoleAssociation(username, roleEnum);
        return ResponseEntity.ok(createdUserRoleAssociation);
    }

    @RequestMapping(value = "/users/{username}/roles/{roleEnum}", method = RequestMethod.DELETE, produces = {APPLICATION_JSON})
    public ResponseEntity<Object> deleteUserRoleAssociation(@PathVariable("username") String username,
                                                            @PathVariable("roleEnum") String roleEnum) throws CustomException {
        userRolesService.deleteUserRoleAssociation(username, roleEnum);
        return ResponseEntity.noContent().build();
    }
}
