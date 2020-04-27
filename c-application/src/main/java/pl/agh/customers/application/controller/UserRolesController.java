package pl.agh.customers.application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = UserController.PREFIX)
public class UserRolesController {

    static final String PREFIX = "/users";

}
