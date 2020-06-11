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
    @WithMockUser(value = "user997")
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
    @WithMockUser(value = "anotherUser")
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

    @Test
    @WithMockUser("10")
    public void loggedInNotFoundTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/10"))
                .andExpect(status().is(404));
    }

    @Test
    @WithMockUser(value = "anotherUser")
    public void otherNotFoundTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users/10"))
                .andExpect(status().is(403));
    }

}
