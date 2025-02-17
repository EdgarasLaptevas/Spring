package lt.techin.carsRental.dto;

import lt.techin.carsRental.model.Role;

import java.util.List;

public class RoleMapper {

    public static Role toRole(RoleRequestDTO roleRequestDTO) {
        return new Role(roleRequestDTO.name());
    }

    public static RoleReturnDTO toRoleDTO(Role role) {
        return new RoleReturnDTO(role.getId(), role.getName());
    }

    public static List<RoleReturnDTO> toRoleListDTO(List<Role> roles) {
        return roles.stream()
                .map((role) -> new RoleReturnDTO(role.getId(), role.getName()))
                .toList();
    }
}
