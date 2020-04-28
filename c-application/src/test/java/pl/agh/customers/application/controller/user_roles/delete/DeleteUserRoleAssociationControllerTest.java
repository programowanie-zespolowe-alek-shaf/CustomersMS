package pl.agh.customers.application.controller.user_roles.delete;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.agh.customers.mysql.entity.UserRoles;
import pl.agh.customers.mysql.repository.UserRolesRepository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.agh.customers.application.config.TestUtils.getIdFromResponse;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DeleteUserRoleAssociationControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRolesRepository userRolesRepository;

    @Test
    public void successCreateAndDeleteAssociationTest() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/users/user999/roles/ROLE_ADmin"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("id").isNumber())
                .andExpect(jsonPath("username").value("user999"))
                .andExpect(jsonPath("roleEnum").value("ROLE_ADMIN"))
                .andReturn();

        Long userRolesId = getIdFromResponse(mvcResult);
        UserRoles userRoles = userRolesRepository.findById(userRolesId).orElse(null);
        assertNotNull(userRoles);

        mvc.perform(MockMvcRequestBuilders.delete("/users/user999/roles/roLE_ADmin"))
                .andExpect(status().is(204));

        userRoles = userRolesRepository.findById(userRolesId).orElse(null);
        assertNull(userRoles);
    }

    @Test
    public void associationDoesNotExistFailedTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/users/user999/roles/rOLE_admiN"))
                .andExpect(status().is(404))
                .andExpect(jsonPath("error").value("User role association does not exist"));
    }

    @Test
    public void userDoesNotExistFailedTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/users/someUser/roles/ROLE_ADMIN"))
                .andExpect(status().is(404))
                .andExpect(jsonPath("error").value("User does not exist"));
    }

    @Test
    public void roleEnumDoesNotExistFailedTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/users/user997/roles/ROLE_super_admin"))
                .andExpect(status().is(404))
                .andExpect(jsonPath("error").value("Role does not exist"));
    }
}
