package lt.techin.runningClub.dto;

import lt.techin.runningClub.model.Role;

import java.util.List;

public class RoleMapper {

    public static Role toRole(RoleRequestDTO roleRequestDTO) {
        return new Role(roleRequestDTO.name());
    }

    public static RoleResponseDTO toRoleDTO(Role role) {
        return new RoleResponseDTO(role.getId(), role.getName());
    }

    public static List<RoleResponseDTO> toRoleListDTO(List<Role> roles) {
        return roles.stream()
                .map((role) -> new RoleResponseDTO(role.getId(), role.getName()))
                .toList();
    }
}
