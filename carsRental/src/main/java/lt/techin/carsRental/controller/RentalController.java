package lt.techin.carsRental.controller;

import jakarta.validation.Valid;
import lt.techin.carsRental.dto.*;
import lt.techin.carsRental.model.Car;
import lt.techin.carsRental.model.Rental;
import lt.techin.carsRental.model.User;
import lt.techin.carsRental.service.RentalService;
import lt.techin.carsRental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RentalController {

    public final RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/rentals")
    public ResponseEntity<?> addRental(@Valid @RequestBody RentalRequestDTO rentalRequestDTO, Authentication authentication) {

        Optional<Car> carOptional = rentalService.carService.findCarById(rentalRequestDTO.car().getId());
        Optional<User> userOptional = rentalService.userService.findUserByUsername(authentication.getName());

        // If car or user are not found, return a BAD_REQUEST response
        if (carOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Car not found with the provided ID");
        }
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found with the provided ID");
        }

        Car car = carOptional.get();
        User user = userOptional.get();


        Rental rental = new Rental(user, car, rentalRequestDTO.rentalStart(), rentalRequestDTO.rentalEnd());

        if (rental.getCar().getStatus().equalsIgnoreCase("unavailable")) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This car is unavailable");
        }

        if (rental.getUser().getRentals().size() >= 2) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You cannot rent more than 2 cars");
        }

        if (rental.rentingDays() < 1) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You must rent this car at least for a day");
        }

        rental.getCar().setStatus("UNAVAILABLE");

        Rental savedRental = rentalService.saveRental(rental);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("{id}")
                        .buildAndExpand(savedRental.getId())
                        .toUri())
                .body(RentalMapper.toRentalDTO(savedRental));

    }

    @PostMapping("/rentals/return/{rentalId}")
    public ResponseEntity<?> rentalReturn(@PathVariable long rentalId, @Valid @RequestBody RentalRequestDTO rentalRequestDTO, Authentication authentication) {

        Optional<Rental> existingRentalOpt = rentalService.findRentalById(rentalId);

        if (existingRentalOpt.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rental not found");
        }

        Rental existingRental = existingRentalOpt.get();

        if (!rentalService.existCarById(rentalRequestDTO.car().getId())) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This car was not rented");
        }

        if (existingRental.getCar().getStatus().equalsIgnoreCase("AVAILABLE")) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You did not rent this car");
        }

        if (existingRental.getUser().getId() != rentalService.userService.findUserByUsername(authentication.getName()).get().getId()) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("This car was not rented by the user.");
        }

        if (rentalRequestDTO.rentalEnd().before(existingRental.getRentalEnd())) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You cannot return car before rental ends");
        }

        Car car = existingRental.getCar();
        car.setStatus("AVAILABLE");
        rentalService.carService.saveCar(car);

        existingRental.setRentalEnd(rentalRequestDTO.rentalEnd());
        existingRental.setPrice(new BigDecimal(existingRental.rentingDays() * 55));
        rentalService.saveRental(existingRental);


        return ResponseEntity.ok(RentalMapper.toCarReturnDTO(existingRental));
    }

    @GetMapping("/rentals/my")
    public ResponseEntity<?> getMyRentalsHistory(Authentication authentication) {

        String usernameName = authentication.getName();

        Optional<User> userRentalsOpt = rentalService.userService.findUserByUsername(usernameName);

        if (userRentalsOpt.isEmpty()) {

            return ResponseEntity.notFound().build();
        }

        User userRentals = userRentalsOpt.get();

        if (rentalService.findRentalListByUser(userRentals).isEmpty()) {

            return ResponseEntity.status(HttpStatus.FOUND).body("My rentals list is empty");
        }

        return ResponseEntity.ok(RentalMapper.toRentalListDTO(rentalService.findRentalListByUser(userRentals)));
    }

    @GetMapping("/rentals/history")
    public ResponseEntity<?> getRentalsHistory() {

        if (rentalService.findAllRentals().isEmpty()) {

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Rentals list is empty");
        }

        return ResponseEntity.ok(RentalMapper.toRentalListDTO(rentalService.findAllRentals()));
    }

}
