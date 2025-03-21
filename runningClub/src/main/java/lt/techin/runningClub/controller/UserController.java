package lt.techin.runningClub.controller;

import jakarta.validation.Valid;
import lt.techin.runningClub.dto.UserMapper;
import lt.techin.runningClub.dto.UserRequestDTO;
import lt.techin.runningClub.dto.UserResponseDTO;
import lt.techin.runningClub.model.User;
import lt.techin.runningClub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/auth/register")
    public ResponseEntity<UserResponseDTO> addUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {

        User savedUser = UserMapper.toUser(userRequestDTO);
        savedUser.setPassword(passwordEncoder.encode(savedUser.getPassword()));
        userService.saveUser(savedUser);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedUser.getId())
                        .toUri())
                .body(UserMapper.toUserDTO(savedUser));

    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getALlUsers() {

        return ResponseEntity.ok(UserMapper.toListDTO(userService.findAllUsers()));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable long id) {

        if (!userService.userExistById(id)) {
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(UserMapper.toUserDTO(userService.findUserById(id).get()));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {

        if (!userService.userExistById(id)) {
            return ResponseEntity.notFound().build();
        }

        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}

