package lt.techin.books.controller;

import lt.techin.books.dto.BookMapperReturn;
import lt.techin.books.dto.UserDTO;
import lt.techin.books.dto.UserMapper;
import lt.techin.books.dto.UserReturnDTO;
import lt.techin.books.model.User;
import lt.techin.books.service.BookService;
import lt.techin.books.service.UserService;
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


    @PostMapping("/users")
    public ResponseEntity<UserReturnDTO> addUser(@RequestBody UserDTO userDTO) {

        User savedUser = UserMapper.toUser(userDTO);
        savedUser.setPassword(passwordEncoder.encode(savedUser.getPassword()));
        userService.saveUser(savedUser);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(savedUser.getId())
                        .toUri())
                .body(UserMapper.userToDTO(savedUser));

    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findALlUsers() {
        return ResponseEntity.ok(userService.findUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserReturnDTO> findUserById(@PathVariable long id) {
        if (!userService.userExistById(id)) {
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(UserMapper.userToDTO(userService.findUser(id).get()));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable long id) {
        if (!userService.userExistById(id)) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}


