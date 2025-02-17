package lt.techin.carsRental.dto;

import lt.techin.carsRental.model.User;

import java.util.List;

public class UserMapper {

    public static User toUser(UserRequestDTO userDTO) {
        return new User(userDTO.username(),
                userDTO.password(), userDTO.roles(), userDTO.rentals());
    }

    public static UserReturnDTO toUserDTO(User user) {
        return new UserReturnDTO(user.getId(), user.getUsername(), user.getRoles(), user.getRentals());
    }

    public static List<UserReturnDTO> toListDTO(List<User> users) {
        return users.stream()
                .map((user) -> new UserReturnDTO(user.getId(), user.getUsername(),
                        user.getRoles(), user.getRentals()))
                .toList();
    }
}
