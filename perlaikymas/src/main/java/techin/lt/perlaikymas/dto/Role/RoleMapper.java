package techin.lt.perlaikymas.dto.Role;

import techin.lt.perlaikymas.model.Role;

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
