package lt.techin.runningClub.dto;

import lt.techin.runningClub.model.Registration;
import lt.techin.runningClub.model.RunningEvent;
import lt.techin.runningClub.model.User;

import java.time.LocalDateTime;

public class RegistrationMapper {

    public static Registration toRegistration(User user, RunningEvent runningEvent) {

        return new Registration(
                user, runningEvent, LocalDateTime.now()
        );
    }

    public static RegistrationResponseDTO toRegistrationResponseDTO(Registration registration) {

        return new RegistrationResponseDTO(registration.getId(), registration.getUser().getId(),
                registration.getRunningEvent().getName(), registration.getRegistrationDate());
    }
}
