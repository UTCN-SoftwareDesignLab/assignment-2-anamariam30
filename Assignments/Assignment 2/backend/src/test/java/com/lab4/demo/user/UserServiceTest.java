package com.lab4.demo.user;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.lab4.demo.TestCreationFactory.*;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(passwordEncoder, userRepository, roleRepository, userMapper);
    }


    @Test
    void create() {
        String role = "EMPLOYEE";
        UserDTO user = UserDTO.builder()
                .id(randomLong())
                .name(randomString())
                .password(randomString())
                .email(randomString())
                .roles(role)
                .build();
        User newUser = User.builder()
                .id(user.getId())
                .username(user.getName())
                .password(user.getPassword())
                .email(user.getEmail())
                .roles(Set.of(Role.builder().name(ERole.valueOf(user.getRoles())).id(randomLong()).build()))
                .build();
        UserMinimalDTO userMinimalDTO=UserMinimalDTO.builder()
                .name(user.getName())
                .id(newUser.getId())
                .build();
        when(roleRepository.findByName(any())).thenReturn(Optional.of(Role.builder().name(ERole.EMPLOYEE).build()));
        when(userRepository.save(any())).thenReturn(newUser);
        when(userMapper.userMinimalFromUser(newUser)).thenReturn(userMinimalDTO);
        UserMinimalDTO expected = userService.create(user);
        Assertions.assertEquals(expected, userMinimalDTO);
    }

    @Test
    void delete() {
        String role = "EMPLOYEE";
        UserDTO userDTO = UserDTO.builder()
                .id(randomLong())
                .name(randomString())
                .password(randomString())
                .email(randomString())
                .roles(role)
                .build();
        userService.delete(userDTO.getId());
    }

    @Test
    void edit(){
        String role = "EMPLOYEE";
        UserDTO userDTO = UserDTO.builder()
                .id(randomLong())
                .name(randomString())
                .password(randomString())
                .email(randomString())
                .roles(role)
                .build();
        User user = User.builder()
                .username(userDTO.getName())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .roles(Set.of(Role.builder().name(ERole.valueOf(userDTO.getRoles())).id(randomLong()).build()))
                .build();
        UserMinimalDTO userMinimalDTO=UserMinimalDTO.builder()
                .name(userDTO.getName())
                .id(userDTO.getId())
                .build();
        when(roleRepository.findByName(any())).thenReturn(Optional.of(Role.builder().name(ERole.EMPLOYEE).id(randomLong()).build()));
        when(userRepository.save(any())).thenReturn(user);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(userMapper.userMinimalFromUser(user)).thenReturn(userMinimalDTO);

        Assertions.assertEquals(userService.edit(userDTO.getId(),userDTO.getName()), userMinimalDTO);
    }
}
