package techin.lt.perlaikymas.dto.User;

import jakarta.validation.constraints.*;
import techin.lt.perlaikymas.model.Review;
import techin.lt.perlaikymas.model.Role;

import java.util.List;

public record UserRequestDTO(@NotNull
                             @Size(min = 4, max = 255, message = "Username must me between 4 and 255 characters")
                             @Pattern(regexp = "^[a-z0-9]+$", message = "username must be in lower cases letters or numbers")
                             String username,

                             @NotNull()
                             @Size(min = 6, max = 255, message = "Password must me between 6 and 255 characters")
                             String password,

                             @NotNull(message = "Roles cannot be null")
                             List<Role> roles
) {
}
