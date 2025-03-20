package techin.lt.perlaikymas.dto.User;

import techin.lt.perlaikymas.model.User;

import java.util.List;

public class UserMapper {

    public static User toUser(UserRequestDTO userRequestDTO) {
        return new User(userRequestDTO.password(), userRequestDTO.username(),
                userRequestDTO.roles(), null);
    }

    public static UserResponseDTO toUserResponseDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getRoles());
    }

    public static List<UserResponseDTO> toUserListResponseDTO(List<User> users) {
        return users.stream()
                .map(UserMapper::toUserResponseDTO)
                .toList();
    }
}
