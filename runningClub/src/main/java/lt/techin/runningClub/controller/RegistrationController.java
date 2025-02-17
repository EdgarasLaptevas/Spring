package lt.techin.runningClub.controller;


import jakarta.validation.Valid;
import lt.techin.runningClub.dto.*;
import lt.techin.runningClub.model.Registration;
import lt.techin.runningClub.model.RunningEvent;
import lt.techin.runningClub.model.User;
import lt.techin.runningClub.service.RegistrationService;
import lt.techin.runningClub.service.RunningEventService;
import lt.techin.runningClub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final RunningEventService runningEventService;
    private final UserService userService;

    @Autowired
    public RegistrationController(RegistrationService registrationService, RunningEventService runningEventService, UserService userService) {
        this.registrationService = registrationService;
        this.runningEventService = runningEventService;
        this.userService = userService;
    }

    @PostMapping("/events/{eventId}/register")
    public ResponseEntity<?> addRunningEvent(@PathVariable long eventId, Authentication authentication) {

        Optional<RunningEvent> runningEventOpt = runningEventService.findRunningEventById(eventId);

        if (runningEventOpt.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This event does not exist");
        }

        RunningEvent runningEvent = runningEventOpt.get();

        Optional<User> userOpt = userService.findUserByUsername(authentication.getName());

        if (userOpt.isEmpty()) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }

        User user = userOpt.get();

        boolean alreadyRegistered = user.getRegistrations().stream()
                .anyMatch(registration -> registration.getRunningEvent().getId() == eventId);

        if (alreadyRegistered) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You cannot register for the same event again");
        }

        Registration savedRegistration = registrationService.saveRegistration(RegistrationMapper.toRegistration(user, runningEvent));


        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("{id}")
                        .buildAndExpand(savedRegistration.getId())
                        .toUri())
                .body(RegistrationMapper.toRegistrationResponseDTO(savedRegistration));

    }
}
