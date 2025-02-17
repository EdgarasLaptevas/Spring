package lt.techin.runningClub.dto;

import lt.techin.runningClub.model.RunningEvent;
import lt.techin.runningClub.model.User;

import java.util.List;

public class RunningEventMapper {

    public static RunningEvent toRunningEvent(RunningEventRequestDTO runningEventRequestDTO) {

        return new RunningEvent(runningEventRequestDTO.name(), runningEventRequestDTO.calendarDate(),
                runningEventRequestDTO.location(), runningEventRequestDTO.maxParticipants(), runningEventRequestDTO.registrations());
    }

    public static RunningEventResponseDTO toRunningEventResponseDTO(RunningEvent runningEvent) {

        return new RunningEventResponseDTO(runningEvent.getId(), runningEvent.getName(),
                runningEvent.getCalendarDate(), runningEvent.getLocation(), runningEvent.getMaxParticipants());
    }

    public static List<RunningEventResponseDTO> runningEventResponseListDTO(List<RunningEvent> runningEvents) {

        return runningEvents.stream()
                .map((runningEvent) -> new RunningEventResponseDTO(runningEvent.getId(),
                        runningEvent.getName(), runningEvent.getCalendarDate(), runningEvent.getLocation(),
                        runningEvent.getMaxParticipants()))
                .toList();

    }

    public List<RunningEventParticipantsResponseDTO> runningEventParticipantsResponseListDTO(List<User> users) {

        return users.stream()
                .map((user) -> new RunningEventParticipantsResponseDTO(user.getId(), user.getUsername()))
                .toList();
    }
}

