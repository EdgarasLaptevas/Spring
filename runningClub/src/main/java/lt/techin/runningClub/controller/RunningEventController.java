package lt.techin.runningClub.controller;

import jakarta.validation.Valid;
import lt.techin.runningClub.dto.RunningEventMapper;
import lt.techin.runningClub.dto.RunningEventRequestDTO;
import lt.techin.runningClub.dto.RunningEventResponseDTO;
import lt.techin.runningClub.dto.RunningEventParticipantsRequestDTO;
import lt.techin.runningClub.model.RunningEvent;
import lt.techin.runningClub.model.User;
import lt.techin.runningClub.service.RunningEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
public class RunningEventController {

    public final RunningEventService runningEventService;

    @Autowired
    public RunningEventController(RunningEventService runningEventService) {
        this.runningEventService = runningEventService;
    }


    @PostMapping("/events")
    public ResponseEntity<RunningEventResponseDTO> addRunningEvent(@Valid @RequestBody RunningEventRequestDTO runningEventRequestDTO) {

        RunningEvent savedRunningEvent = runningEventService.saveRunningEvent(RunningEventMapper.toRunningEvent(runningEventRequestDTO));

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("{id}")
                        .buildAndExpand(savedRunningEvent.getId())
                        .toUri())
                .body(RunningEventMapper.toRunningEventResponseDTO(savedRunningEvent));

    }

    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable long eventId) {

        if (!runningEventService.runningEventExistById(eventId)) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This even does not exist");
        }

        runningEventService.deleteRunningEventById(eventId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("events")
    public ResponseEntity<List<RunningEventResponseDTO>> getAllRunningEvents() {

        List<RunningEvent> allRunningEvents = runningEventService.findAllRunningEvents();

        return ResponseEntity.ok(RunningEventMapper.runningEventResponseListDTO(allRunningEvents));
    }

    @GetMapping("/events/{eventId}/participants")
    public ResponseEntity<?> getAllEventsParticipants(@PathVariable long eventId) {

      Optional<RunningEvent> runningEventOpt =  runningEventService.findRunningEventById(eventId);

               if (runningEventOpt.isEmpty()) {

                   return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("this even does not exist");
               }

               RunningEvent runningEvent = runningEventOpt.get();

              List<User> registeredParticipants = runningEvent.getRegistrations().stream()
                       .map((registration) -> registration.getUser())
                      .toList();

              return ResponseEntity.ok()
    }

}
