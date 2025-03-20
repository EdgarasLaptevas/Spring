package techin.lt.perlaikymas.dto.Role;

import jakarta.validation.constraints.NotNull;

public record RoleRequestDTO(@NotNull
                             String name) {
}
