package com.lab4.demo.user;

import com.lab4.demo.security.AuthService;
import com.lab4.demo.security.dto.MessageResponse;
import com.lab4.demo.user.dto.UserDTO;
import com.lab4.demo.user.dto.UserListDTO;
import com.lab4.demo.user.dto.UserMinimalDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.ENTITY;
import static com.lab4.demo.UrlMapping.USER;

@RestController
@RequestMapping(USER)
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @PatchMapping
    public UserMinimalDTO edit(@RequestBody @NonNull UserMinimalDTO userMinimalDTO) {
        return userService.edit(userMinimalDTO.getId(),userMinimalDTO.getName());
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable("id") @NonNull Long id) {
        userService.delete(id);
    }

    @PostMapping
    public ResponseEntity<?>  create(@RequestBody @NonNull UserDTO user) {
        if (authService.existsByUsername(user.getName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (authService.existsByEmail(user.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        userService.create(user);
        return ResponseEntity.ok(new MessageResponse("User added successfully!"));
    }

}
