package lt.techin.runningClub.service;

import lt.techin.runningClub.model.RunningEvent;
import lt.techin.runningClub.repository.RunningEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RunningEventService {

    public final RunningEventRepository runningEventRepository;

    @Autowired
    public RunningEventService(RunningEventRepository runningEventRepository) {
        this.runningEventRepository = runningEventRepository;
    }

    public RunningEvent saveRunningEvent(RunningEvent runningEvent) {
        return runningEventRepository.save(runningEvent);
    }

    public List<RunningEvent> findAllRunningEvents() {
        return runningEventRepository.findAll();
    }

    public Optional<RunningEvent> findRunningEventById(long id) {
        return runningEventRepository.findById(id);
    }

    public boolean runningEventExistById(long id) {
        return runningEventRepository.existsById(id);
    }

    public void deleteRunningEventById(long id) {
        runningEventRepository.deleteById(id);
    }

//    public List<Car> findAllAvailableCars(String status) {
//        return carRepository.findAllByStatus(status);
//    }
}
