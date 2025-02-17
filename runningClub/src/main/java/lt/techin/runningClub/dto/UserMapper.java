package lt.techin.runningClub.dto;

import lt.techin.runningClub.model.User;

import java.util.List;

public class UserMapper {

    public static User toUser(UserRequestDTO userRequestDTO) {
        return new User(userRequestDTO.username(),
                userRequestDTO.password(), userRequestDTO.roles(), userRequestDTO.registrations());
    }

    public static UserResponseDTO toUserDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getRoles());
    }

    public static List<UserResponseDTO> toListDTO(List<User> users) {
        return users.stream()
                .map((user) -> new UserResponseDTO(user.getId(), user.getUsername(),
                        user.getRoles()))
                .toList();
    }
}
