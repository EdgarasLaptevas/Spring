package lt.techin.runningClub.dto;

import jakarta.validation.constraints.NotNull;

public record RoleRequestDTO(@NotNull
                             String name) {
}
