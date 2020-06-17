package pl.agh.customers.application.controller.user_roles.create;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.agh.customers.application.rest.RestClient;
import pl.agh.customers.mysql.entity.UserRoles;
import pl.agh.customers.mysql.enums.RoleEnum;
import pl.agh.customers.mysql.repository.UserRolesRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.agh.customers.application.config.TestUtils.getIdFromResponse;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(roles = "ADMIN")
public class AddUserRoleAssociationControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRolesRepository userRolesRepository;
    @MockBean
    private RestClient restClient;

    @Test
    public void successTest() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/users/user999/roles/ROLE_ADmin"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("id").isNumber())
                .andExpect(jsonPath("username").value("user999"))
                .andExpect(jsonPath("roleEnum").value("ROLE_ADMIN"))
                .andReturn();

        Long userRolesId = getIdFromResponse(mvcResult);
        UserRoles userRoles = userRolesRepository.findById(userRolesId).orElse(null);
        assertNotNull(userRoles);
        assertEquals("user999", userRoles.getUser().getUsername());
        assertEquals(RoleEnum.ROLE_ADMIN, userRoles.getRoleEnum());

        userRolesRepository.delete(userRoles);
    }

    @Test
    public void associationAlreadyExistsFailedTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/users/user997/roles/rOLE_admiN"))
                .andExpect(status().is(400))
                .andExpect(jsonPath("error").value("User role association already exists"));
    }

    @Test
    public void userDoesNotExistFailedTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/users/someUser/roles/ROLE_ADMIN"))
                .andExpect(status().is(404))
                .andExpect(jsonPath("error").value("User does not exist"));
    }

    @Test
    public void roleEnumDoesNotExistFailedTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/users/user997/roles/ROLE_super_admin"))
                .andExpect(status().is(404))
                .andExpect(jsonPath("error").value("Role does not exist"));
    }
}
