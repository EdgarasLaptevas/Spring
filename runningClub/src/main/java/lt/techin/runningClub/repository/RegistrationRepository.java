package lt.techin.runningClub.repository;

import lt.techin.runningClub.model.Registration;
import lt.techin.runningClub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    List<Registration> findAllByUser(User user);
}
