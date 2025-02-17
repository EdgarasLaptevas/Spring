package lt.techin.books.dto;

import lt.techin.books.model.Role;

import java.util.List;

public record UserReturnDTO(String username, List<Role> roles) {
}
