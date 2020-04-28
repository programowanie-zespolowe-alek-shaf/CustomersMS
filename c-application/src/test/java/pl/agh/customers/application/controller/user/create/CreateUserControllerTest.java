package pl.agh.customers.application.controller.user.create;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.agh.customers.application.dto.UserPostRequestDTO;
import pl.agh.customers.mysql.entity.User;
import pl.agh.customers.mysql.entity.UserRoles;
import pl.agh.customers.mysql.enums.RoleEnum;
import pl.agh.customers.mysql.repository.UserRepository;
import pl.agh.customers.mysql.repository.UserRolesRepository;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.agh.customers.application.config.TestUtils.mapObjectToStringJson;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CreateUserControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRolesRepository userRolesRepository;

    @Test
    public void successTest() throws Exception {
        UserPostRequestDTO userDTO = UserPostRequestDTO.builder()
                .username("newUser")
                .password("pass")
                .firstName("A")
                .lastName("B")
                .email("2@c.com")
                .phone("4533453")
                .address("ggd, gfd")
                .enabled(false)
                .build();

        String requestJson = mapObjectToStringJson(userDTO);

        mvc.perform(MockMvcRequestBuilders.post("/users").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().is(201))
                .andExpect(jsonPath("username").value("newUser"))
                .andExpect(jsonPath("password").doesNotExist())
                .andExpect(jsonPath("firstName").value("A"))
                .andExpect(jsonPath("lastName").value("B"))
                .andExpect(jsonPath("email").value("2@c.com"))
                .andExpect(jsonPath("phone").value("4533453"))
                .andExpect(jsonPath("address").value("ggd, gfd"))
                .andExpect(jsonPath("enabled").value(false))
                .andExpect(jsonPath("roles[0]").value("ROLE_USER"));

        User user = userRepository.findById("newUser").orElse(null);
        assertNotNull(user);
        assertEquals("newUser", user.getUsername());
        assertEquals("pass", user.getPassword());
        assertEquals("A", user.getFirstName());
        assertEquals("B", user.getLastName());
        assertEquals("2@c.com", user.getEmail());
        assertEquals("4533453", user.getPhone());
        assertEquals("ggd, gfd", user.getAddress());
        assertFalse(user.getEnabled());

        List<UserRoles> userRolesList = userRolesRepository.findByUser(user);
        assertEquals(1, userRolesList.size());
        assertEquals(RoleEnum.ROLE_USER, userRolesList.get(0).getRoleEnum());

        userRolesRepository.deleteAll(userRolesList);
        userRepository.delete(user);
    }

    @Test
    public void noUsernameFailedTest() throws Exception {
        UserPostRequestDTO userDTO = UserPostRequestDTO.builder()
                .password("pass")
                .firstName("A")
                .lastName("B")
                .email("2@c.com")
                .phone("4533453")
                .address("ggd, gfd")
                .enabled(false)
                .build();

        String requestJson = mapObjectToStringJson(userDTO);

        mvc.perform(MockMvcRequestBuilders.post("/users").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().is(400))
                .andExpect(jsonPath("error").value("username cannot be null"));
    }

    @Test
    public void noPhoneFailedTest() throws Exception {
        UserPostRequestDTO userDTO = UserPostRequestDTO.builder()
                .username("FDSfd")
                .password("pass")
                .firstName("A")
                .lastName("B")
                .email("2@c.com")
                .address("ggd, gfd")
                .enabled(false)
                .build();

        String requestJson = mapObjectToStringJson(userDTO);

        mvc.perform(MockMvcRequestBuilders.post("/users").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().is(400))
                .andExpect(jsonPath("error").value("phone cannot be null"));
    }

    @Test
    public void invalidEmailFormatFailedTest() throws Exception {
        UserPostRequestDTO userDTO = UserPostRequestDTO.builder()
                .username("FDSfd")
                .password("pass")
                .firstName("A")
                .lastName("B")
                .email("2dc.com")
                .phone("535434")
                .address("ggd, gfd")
                .enabled(false)
                .build();

        String requestJson = mapObjectToStringJson(userDTO);

        mvc.perform(MockMvcRequestBuilders.post("/users").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().is(400))
                .andExpect(jsonPath("error").value("Email format is invalid"));
    }

    @Test
    public void invalidPhoneFormatFailedTest() throws Exception {
        UserPostRequestDTO userDTO = UserPostRequestDTO.builder()
                .username("FDSfd")
                .password("pass")
                .firstName("A")
                .lastName("B")
                .email("2@dc.com")
                .phone("535fdsg4")
                .address("ggd, gfd")
                .enabled(false)
                .build();

        String requestJson = mapObjectToStringJson(userDTO);

        mvc.perform(MockMvcRequestBuilders.post("/users").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().is(400))
                .andExpect(jsonPath("error").value("Phone format is invalid"));
    }

    @Test
    public void userAlreadyExistsTest() throws Exception {
        UserPostRequestDTO userDTO = UserPostRequestDTO.builder()
                .username("user997")
                .password("pass")
                .firstName("A")
                .lastName("B")
                .email("2@c.com")
                .phone("4533453")
                .address("ggd, gfd")
                .enabled(false)
                .build();

        String requestJson = mapObjectToStringJson(userDTO);

        mvc.perform(MockMvcRequestBuilders.post("/users").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().is(400))
                .andExpect(jsonPath("error").value("Username is used"));
    }
}
