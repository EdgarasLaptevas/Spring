package lt.techin.books.dto;

import lt.techin.books.model.User;

import java.util.List;

public class UserMapper {

    public static List<UserDTO> userListToDTO(List<User> users) {
        return users.stream()
                .map((user) -> new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getRoles()))
                .toList();

    }

    public static UserReturnDTO userToDTO(User user) {
        return new UserReturnDTO(user.getUsername(), user.getRoles());
    }

    public static User toUser(UserDTO userDTO) {
        return new User(userDTO.username(), userDTO.password(), userDTO.roles());
    }
}
