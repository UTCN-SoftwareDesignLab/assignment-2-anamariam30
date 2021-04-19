package com.lab4.demo.user;

import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import com.lab4.demo.user.mapper.UserMapper;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder encoder;

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {

        List<UserListDTO> collect = userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
        return collect;
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public UserMinimalDTO edit(Long id, String username) {
        User actUser = findById(id);
        actUser.setUsername(username);
        return save(actUser);


    }

    public UserMinimalDTO save(User user) {


        return userMapper.userMinimalFromUser(userRepository.save(user));
    }

    public UserMinimalDTO create(UserDTO user) {
        String role = user.getRoles();
        Set<Role> roles = new HashSet<>();
        ERole eRole = ERole.valueOf(role);
        Role e = roleRepository.findByName(eRole).get();
        roles.add(e);
        User newUser = User.builder().username(user.getName())
                .email(user.getEmail())
                .roles(roles)
                .password(encoder.encode(user.getPassword())).build();
        User save = userRepository.save(newUser);
        return userMapper.userMinimalFromUser(save);
    }
}
