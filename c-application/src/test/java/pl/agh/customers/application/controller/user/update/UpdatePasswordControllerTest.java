package pl.agh.customers.application.controller.user.update;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.agh.customers.application.config.WithCustomUser;
import pl.agh.customers.mysql.entity.User;
import pl.agh.customers.mysql.repository.UserRepository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UpdatePasswordControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void adminSuccessTest() throws Exception {
        User userBefore = userRepository.findById("user999").orElseThrow(null);

        mvc.perform(MockMvcRequestBuilders.patch("/users/user999")
                .param("newPassword", "newPass"))
                .andExpect(status().is(204))
                .andExpect(jsonPath("username").doesNotExist());

        User user = userRepository.findById("user999").orElse(null);
        assertNotNull(user);
        assertTrue(new BCryptPasswordEncoder().matches("newPass", user.getPassword()));

        userRepository.save(userBefore);
    }

    @Test
    @WithCustomUser("user999")
    public void loggedInSuccessTest() throws Exception {
        User userBefore = userRepository.findById("user999").orElseThrow(null);

        mvc.perform(MockMvcRequestBuilders.patch("/users/user999")
                .param("newPassword", "newPass"))
                .andExpect(status().is(204))
                .andExpect(jsonPath("username").doesNotExist());

        User user = userRepository.findById("user999").orElse(null);
        assertNotNull(user);
        assertTrue(new BCryptPasswordEncoder().matches("newPass", user.getPassword()));

        userRepository.save(userBefore);
    }

    @Test
    @WithCustomUser("user997")
    public void otherSuccessTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.patch("/users/user999")
                .param("newPassword", "newPass"))
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void adminUserWithSpecifiedIdDoesNotExistFailedTestAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.patch("/users/10")
                .param("newPassword", "newUrl"))
                .andExpect(status().is(404));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void adminNewPasswordIsNotSpecifiedFailedTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.patch("/users/user999"))
                .andExpect(status().is(400))
                .andExpect(status().reason("Required String parameter 'newPassword' is not present"));
    }

    @Test
    @WithCustomUser("user999")
    public void loggedInNewPasswordIsNotSpecifiedFailedTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.patch("/users/user999"))
                .andExpect(status().is(400))
                .andExpect(status().reason("Required String parameter 'newPassword' is not present"));
    }
}
