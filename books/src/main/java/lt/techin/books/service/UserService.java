package lt.techin.books.service;

import lt.techin.books.model.User;
import lt.techin.books.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public boolean userExistById(long id) {
        return userRepository.existsById(id);
    }

    public Optional<User> findUser(long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }
}
