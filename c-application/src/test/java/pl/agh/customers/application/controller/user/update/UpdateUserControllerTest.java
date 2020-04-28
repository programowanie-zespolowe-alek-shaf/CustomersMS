package pl.agh.customers.application.controller.user.update;

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
import pl.agh.customers.application.dto.UserPutRequestDTO;
import pl.agh.customers.mysql.entity.User;
import pl.agh.customers.mysql.repository.UserRepository;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.agh.customers.application.config.TestUtils.mapObjectToStringJson;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UpdateUserControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void successUpdateUserTest() throws Exception {
        User userBefore = userRepository.findById("user997").orElseThrow(null);

        UserPutRequestDTO userRequestDTO = UserPutRequestDTO.builder()
                .firstName("A")
                .lastName("B")
                .email("2@c.com")
                .phone("4533453")
                .address("ggd, gfd")
                .enabled(false)
                .build();

        String requestJson = mapObjectToStringJson(userRequestDTO);

        mvc.perform(MockMvcRequestBuilders.put("/users/user997").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().is(200))
                .andExpect(jsonPath("username").value("user997"))
                .andExpect(jsonPath("password").doesNotExist())
                .andExpect(jsonPath("firstName").value("A"))
                .andExpect(jsonPath("lastName").value("B"))
                .andExpect(jsonPath("email").value("2@c.com"))
                .andExpect(jsonPath("phone").value("4533453"))
                .andExpect(jsonPath("address").value("ggd, gfd"))
                .andExpect(jsonPath("enabled").value(false))
                .andExpect(jsonPath("roles[0]").value("ROLE_USER"))
                .andExpect(jsonPath("roles[1]").value("ROLE_ADMIN"));

        User user = userRepository.findById("user997").orElse(null);
        assertNotNull(user);
        assertEquals("user997", user.getUsername());
        assertEquals("okon", user.getPassword());
        assertEquals("A", user.getFirstName());
        assertEquals("B", user.getLastName());
        assertEquals("2@c.com", user.getEmail());
        assertEquals("4533453", user.getPhone());
        assertEquals("ggd, gfd", user.getAddress());
        assertFalse(user.getEnabled());

        userRepository.save(userBefore);
    }

    @Test
    public void noLastNameFailedTest() throws Exception {
        UserPutRequestDTO userRequestDTO = UserPutRequestDTO.builder()
                .firstName("A")
                .email("2@c.com")
                .phone("4533453")
                .address("ggd, gfd")
                .enabled(false)
                .build();

        String requestJson = mapObjectToStringJson(userRequestDTO);

        mvc.perform(MockMvcRequestBuilders.put("/users/user997").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().is(400))
                .andExpect(jsonPath("error").value("lastName cannot be null"));
    }

    @Test
    public void noPhoneFailedTest() throws Exception {
        UserPutRequestDTO userRequestDTO = UserPutRequestDTO.builder()
                .firstName("A")
                .lastName("B")
                .email("2@c.com")
                .address("ggd, gfd")
                .enabled(false)
                .build();

        String requestJson = mapObjectToStringJson(userRequestDTO);

        mvc.perform(MockMvcRequestBuilders.put("/users/user997").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().is(400))
                .andExpect(jsonPath("error").value("phone cannot be null"));
    }

    @Test
    public void invalidEmailFormatFailedTest() throws Exception {
        UserPutRequestDTO userRequestDTO = UserPutRequestDTO.builder()
                .firstName("A")
                .lastName("B")
                .email("2dc.com")
                .phone("535434")
                .address("ggd, gfd")
                .enabled(false)
                .build();

        String requestJson = mapObjectToStringJson(userRequestDTO);

        mvc.perform(MockMvcRequestBuilders.put("/users/user997").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().is(400))
                .andExpect(jsonPath("error").value("Email format is invalid"));
    }

    @Test
    public void invalidPhoneFormatFailedTest() throws Exception {
        UserPutRequestDTO userRequestDTO = UserPutRequestDTO.builder()
                .firstName("A")
                .lastName("B")
                .email("2@dc.com")
                .phone("535fdsg4")
                .address("ggd, gfd")
                .enabled(false)
                .build();

        String requestJson = mapObjectToStringJson(userRequestDTO);

        mvc.perform(MockMvcRequestBuilders.put("/users/user997").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().is(400))
                .andExpect(jsonPath("error").value("Phone format is invalid"));
    }

    @Test
    public void userWithSpecifiedIdDoesNotExistTest() throws Exception {
        UserPutRequestDTO userRequestDTO = UserPutRequestDTO.builder()
                .firstName("A")
                .lastName("B")
                .email("2@c.com")
                .phone("4533453")
                .address("ggd, gfd")
                .enabled(false)
                .build();

        String requestJson = mapObjectToStringJson(userRequestDTO);

        mvc.perform(MockMvcRequestBuilders.put("/users/10").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().is(404));
    }
}
