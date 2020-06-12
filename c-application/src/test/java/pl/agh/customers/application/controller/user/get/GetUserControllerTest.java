package pl.agh.customers.application.controller.user.get;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.agh.customers.application.config.WithCustomUser;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class GetUserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void adminSuccessTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/user997"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("username").value("user997"))
                .andExpect(jsonPath("password").doesNotExist())
                .andExpect(jsonPath("firstName").value("John"))
                .andExpect(jsonPath("lastName").value("Doe"))
                .andExpect(jsonPath("email").value("twojastara@gmail.com"))
                .andExpect(jsonPath("phone").value("+48500600700"))
                .andExpect(jsonPath("address").value("123 Elf Road, North Pole, 8888"))
                .andExpect(jsonPath("enabled").value(true))
                .andExpect(jsonPath("lastShoppingCardId").value(123L))
                .andExpect(jsonPath("roles[0]").value("ROLE_USER"))
                .andExpect(jsonPath("roles[1]").value("ROLE_ADMIN"));
    }

    @Test
    @WithCustomUser("user997")
    public void loggedInSuccessTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/user997"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("username").value("user997"))
                .andExpect(jsonPath("password").doesNotExist())
                .andExpect(jsonPath("firstName").value("John"))
                .andExpect(jsonPath("lastName").value("Doe"))
                .andExpect(jsonPath("email").value("twojastara@gmail.com"))
                .andExpect(jsonPath("phone").value("+48500600700"))
                .andExpect(jsonPath("address").value("123 Elf Road, North Pole, 8888"))
                .andExpect(jsonPath("enabled").value(true))
                .andExpect(jsonPath("lastShoppingCardId").value(123L))
                .andExpect(jsonPath("roles[0]").value("ROLE_USER"))
                .andExpect(jsonPath("roles[1]").value("ROLE_ADMIN"));
    }

    @Test
    @WithCustomUser("user999")
    public void loggedInSuccessTestDifferentUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/user999"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("username").value("user999"))
                .andExpect(jsonPath("password").doesNotExist())
                .andExpect(jsonPath("firstName").value("Robert"))
                .andExpect(jsonPath("lastName").value("Smith"))
                .andExpect(jsonPath("email").value("nietwoja@gmail.com"))
                .andExpect(jsonPath("phone").value("+911943243233"))
                .andExpect(jsonPath("address").value("123 Elf Road, North Pole, 8888"))
                .andExpect(jsonPath("enabled").value(true))
                .andExpect(jsonPath("lastShoppingCardId").value(987))
                .andExpect(jsonPath("roles[0]").value("ROLE_USER"));
    }

    @Test
    @WithCustomUser("user999")
    public void otherSuccessTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/user997"))
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void adminNotFoundTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/10"))
                .andExpect(status().is(404));
    }
}
