package lt.techin.runningClub.dto;

import jakarta.validation.constraints.*;
import lt.techin.runningClub.model.Registration;
import lt.techin.runningClub.model.Role;

import java.util.List;

public record UserRequestDTO(@NotNull
                             @Size(min = 4, max = 100, message = "Username must me between 4 and 100 characters")
                             @Pattern(regexp = "^[a-z0-9]+$", message = "username must be in lower cases")
                             String username,

                             @NotNull()
                             @Size(min = 6, max = 150, message = "Password must me between 6 and 150 characters")
                             String password,

                             @NotNull
                             List<Role> roles,

                             List<Registration> registrations) {
}
