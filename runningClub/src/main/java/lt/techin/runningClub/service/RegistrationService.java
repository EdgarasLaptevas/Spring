package lt.techin.runningClub.service;

import lt.techin.runningClub.model.Registration;
import lt.techin.runningClub.model.User;
import lt.techin.runningClub.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;

    @Autowired
    public RegistrationService(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    public Registration saveRegistration(Registration registration) {
        return registrationRepository.save(registration);
    }

    public List<Registration> findAllRegistrations() {
        return registrationRepository.findAll();
    }

    public Optional<Registration> findRegistrationById(long id) {
        return registrationRepository.findById(id);
    }

    public boolean registrationExistById(long id) {
        return registrationRepository.existsById(id);
    }

    public void deleteRegistrationById(long id) {
        registrationRepository.deleteById(id);
    }

    public List<Registration> findRegistrationListByUser(User user) {
        return registrationRepository.findAllByUser(user);
    }
}
