package techin.lt.perlaikymas.dto.User;

import techin.lt.perlaikymas.model.Role;

import java.util.List;

public record UserResponseDTO(long id,
                              String username,
                              List<Role> roles) {
}
