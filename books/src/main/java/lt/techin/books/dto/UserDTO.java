package lt.techin.books.dto;

import lt.techin.books.model.Role;

import java.util.List;

public record UserDTO(
        long id,
        String username,
        String password,
        List<Role> roles
) {

}



