package lt.techin.runningClub.dto;

import jakarta.validation.constraints.*;
import lt.techin.runningClub.model.Registration;


import java.time.LocalDate;
import java.util.List;

public record RunningEventRequestDTO(@NotNull
                                     @Size(min = 5, max = 255, message = "Password must me between 5 and 255 characters")
                                     String name,

                                     @NotNull
                                     @Future
                                     LocalDate calendarDate,

                                     @NotNull
                                     @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "Only letters and numbers are allowed")
                                     String location,

                                     @Min(1)
                                     int maxParticipants,

                                     List<Registration> registrations) {
}
