package pl.agh.customers.application.controller.user.delete;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.agh.customers.application.dto.UserPostRequestDTO;
import pl.agh.customers.mysql.entity.User;
import pl.agh.customers.mysql.entity.UserRoles;
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
public class DeleteUserControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRolesRepository userRolesRepository;

    @Test
    @WithMockUser(value = "user997")
    public void createAndDeleteSuccessTest() throws Exception {
        UserPostRequestDTO userDTO = UserPostRequestDTO.builder()
                .username("userToDelete")
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
                .andExpect(jsonPath("username").value("userToDelete"));

        User user = userRepository.findById("userToDelete").orElse(null);
        assertNotNull(user);
        List<UserRoles> userRolesList = userRolesRepository.findByUser(user);
        assertEquals(1, userRolesList.size());

        mvc.perform(MockMvcRequestBuilders.delete("/users/userToDelete"))
                .andExpect(status().is(204));

        User userAfterDeleting = userRepository.findById("userToDelete").orElse(null);
        assertNull(userAfterDeleting);

        userRolesList = userRolesRepository.findByUser(user);
        assertEquals(0, userRolesList.size());
    }

    @Test
    @WithMockUser(value = "user997")
    public void notFoundTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/users/userDoesNotExist"))
                .andExpect(status().is(404));
    }
}
