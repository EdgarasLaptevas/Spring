package lt.techin.runningClub.dto;

import jakarta.validation.constraints.NotNull;
import lt.techin.runningClub.model.User;

public record RegistrationRequestDTO(@NotNull
                                     User user) {
}
