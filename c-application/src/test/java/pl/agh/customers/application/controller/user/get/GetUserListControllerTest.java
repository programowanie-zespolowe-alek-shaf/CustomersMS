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
public class GetUserListControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(value = "user997")
    public void noLimitAndOffsetTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().is(400))
                .andExpect(status().reason("Required int parameter 'limit' is not present"));
    }

    @Test
    @WithMockUser(value = "user997")
    public void noOffsetTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users")
                .param("limit", "0"))
                .andExpect(status().is(400))
                .andExpect(status().reason("Required int parameter 'offset' is not present"));
    }

    @Test
    @WithMockUser(value = "user997")
    public void onlyLimitAndOffsetTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users")
                .param("offset", "0")
                .param("limit", "10"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("list[0].username").value("user997"))
                .andExpect(jsonPath("list[0].password").doesNotExist())
                .andExpect(jsonPath("list[0].firstName").value("John"))
                .andExpect(jsonPath("list[0].lastName").value("Doe"))
                .andExpect(jsonPath("list[0].email").value("twojastara@gmail.com"))
                .andExpect(jsonPath("list[0].phone").value("+48500600700"))
                .andExpect(jsonPath("list[0].address").value("123 Elf Road, North Pole, 8888"))
                .andExpect(jsonPath("list[0].enabled").value(true))
                .andExpect(jsonPath("list[0].roles[0]").value("ROLE_USER"))
                .andExpect(jsonPath("list[0].roles[1]").value("ROLE_ADMIN"))
                .andExpect(jsonPath("list[0].lastShoppingCardId").value(123L))
                .andExpect(jsonPath("list[1].username").value("user999"))
                .andExpect(jsonPath("list[1].password").doesNotExist())
                .andExpect(jsonPath("list[1].lastShoppingCardId").value(987L))
                .andExpect(jsonPath("list[1].roles[0]").value("ROLE_USER"))
                .andExpect(jsonPath("list[1].roles[1]").doesNotExist())
                .andExpect(jsonPath("count").value("2"));
    }

    @Test
    @WithMockUser(value = "user997")
    public void offset1Test() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users")
                .param("offset", "1")
                .param("limit", "10"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("list[0].username").value("user999"))
                .andExpect(jsonPath("list[1].id").doesNotExist())
                .andExpect(jsonPath("count").value("2"));
    }

    @Test
    @WithMockUser(value = "user997")
    public void limitBelowZeroTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users")
                .param("offset", "0")
                .param("limit", "-3"))
                .andExpect(status().is(400))
                .andExpect(jsonPath("error").value("limit must be greater than zero"));
    }

    @Test
    @WithMockUser(value = "user997")
    public void offsetBelowZeroTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users")
                .param("offset", "-10")
                .param("limit", "20"))
                .andExpect(status().is(400))
                .andExpect(jsonPath("error").value("offset must be greater or equals zero"));
    }
}
