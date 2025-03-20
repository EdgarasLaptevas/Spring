package techin.lt.perlaikymas.service;

import techin.lt.perlaikymas.model.User;
import techin.lt.perlaikymas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    public final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(long id) {
        return userRepository.findById(id);
    }

    public boolean userNotExistById(long id) {
        return !userRepository.existsById(id);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    public boolean userExistByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
