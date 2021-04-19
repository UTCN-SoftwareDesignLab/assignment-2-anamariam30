package com.lab4.demo.user;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.security.AuthController;
import com.lab4.demo.security.AuthService;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.lab4.demo.TestCreationFactory.*;
import static com.lab4.demo.UrlMapping.ENTITY;
import static com.lab4.demo.UrlMapping.USER;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest {

    @InjectMocks
    private UserController userController;
    @InjectMocks
    private AuthService authService;

    @Mock
    private UserService userService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService,authService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void allUsers() throws Exception {
        List<UserListDTO> userListDTOs = TestCreationFactory.listOf(UserListDTO.class);
        when(userService.allUsersForList()).thenReturn(userListDTOs);

        ResultActions result = mockMvc.perform(get(USER));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTOs));
    }

    @Test
    void create() throws Exception{
        UserDTO user = UserDTO.builder()
                .email(randomEmail())
                .name(randomString())
                .password(randomString())
                .build();

        UserMinimalDTO userMinimalDTO=UserMinimalDTO.builder().name(randomString()).id(randomLong()).build();
        when(userService.create(user)).thenReturn(userMinimalDTO);


        ResultActions result = performPostWithRequestBody(USER,user);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(user));
    }

    @Test
    void delete() throws Exception{
        UserDTO user = UserDTO.builder()
                .id(randomLong())
                .email(randomEmail())
                .name(randomString())
                .password(randomString())
                .build();
        ResultActions result = performDeleteWithPathVariable(USER+ENTITY,user.getId().toString());
        result.andExpect(status().isOk());
    }

    @Test
    void edit() throws Exception{
        UserDTO user = UserDTO.builder()
                .id(randomLong())
                .email(randomEmail())
                .name(randomString())
                .password(randomString())
                .build();
        UserMinimalDTO userMinimalDTO= UserMinimalDTO.builder().id(user.getId()).name(user.getName()).build();
        when(userService.edit(user.getId(),user.getName())).thenReturn(userMinimalDTO);
        ResultActions result = performPatchWithRequestBodyAndPathVariable(USER+ENTITY,user.getId().toString(),user);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(user));
    }
}