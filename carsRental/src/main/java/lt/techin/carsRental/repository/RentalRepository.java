package lt.techin.carsRental.repository;

import lt.techin.carsRental.model.Rental;
import lt.techin.carsRental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findAllByUser(User user);
}
