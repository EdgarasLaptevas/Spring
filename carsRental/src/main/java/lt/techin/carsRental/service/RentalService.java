package lt.techin.carsRental.service;

import lt.techin.carsRental.model.Rental;
import lt.techin.carsRental.model.User;
import lt.techin.carsRental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    public final RentalRepository rentalRepository;
    public final CarService carService;
    public final UserService userService;

    @Autowired
    public RentalService(RentalRepository rentalRepository, CarService carService, UserService userService) {
        this.rentalRepository = rentalRepository;
        this.carService = carService;
        this.userService = userService;
    }

    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    public List<Rental> findAllRentals() {
        return rentalRepository.findAll();
    }

    public Optional<Rental> findRentalById(long id) {
        return rentalRepository.findById(id);
    }

    public boolean rentalExistById(long id) {
        return rentalRepository.existsById(id);
    }

    public void deleteRentalById(long id) {
        rentalRepository.deleteById(id);
    }

    public boolean existCarById(long carId) {
        return carService.carExistById(carId);
    }

    public List<Rental> findRentalListByUser(User user) {
        return rentalRepository.findAllByUser(user);
    }
}
