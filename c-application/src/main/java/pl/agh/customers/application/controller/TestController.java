package pl.agh.customers.application.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TestController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public UserDTO getCurrentUsername(Principal principal) {
        return new UserDTO(principal.getName());
    }

    @Data
    @AllArgsConstructor
    static class UserDTO {
        private String username;
    }
}
