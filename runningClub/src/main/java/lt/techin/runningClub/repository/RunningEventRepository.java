package lt.techin.runningClub.repository;

import lt.techin.runningClub.model.RunningEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RunningEventRepository extends JpaRepository<RunningEvent, Long> {
}
