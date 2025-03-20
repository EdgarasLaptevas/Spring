package techin.lt.perlaikymas.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import techin.lt.perlaikymas.dto.User.UserMapper;
import techin.lt.perlaikymas.dto.User.UserRequestDTO;
import techin.lt.perlaikymas.dto.User.UserResponseDTO;
import techin.lt.perlaikymas.model.User;
import techin.lt.perlaikymas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> addUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {

        if (userService.userExistByUsername(userRequestDTO.username())) {

            Map<String, String> result = new HashMap<>();
            result.put("User", "already exist");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        User user = UserMapper.toUser(userRequestDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(user.getId())
                        .toUri())
                .body(UserMapper.toUserResponseDTO(user));

    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getALlUsers() {

        return ResponseEntity.ok(UserMapper.toUserListResponseDTO(userService.findAllUsers()));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable long id) {

        if (!userService.userNotExistById(id)) {
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(UserMapper.toUserResponseDTO(userService.findUserById(id).get()));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {

        if (userService.userNotExistById(id)) {
            return ResponseEntity.notFound().build();
        }

        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}

