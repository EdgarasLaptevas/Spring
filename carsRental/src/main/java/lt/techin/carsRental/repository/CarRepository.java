package lt.techin.carsRental.repository;

import lt.techin.carsRental.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    public List<Car> findAllByStatus(String status);
}
