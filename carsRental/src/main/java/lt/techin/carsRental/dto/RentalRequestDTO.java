package lt.techin.carsRental.dto;

import jakarta.validation.constraints.NotNull;
import lt.techin.carsRental.model.Car;
import lt.techin.carsRental.model.User;

import java.util.Date;

public record RentalRequestDTO(Car car,
                               @NotNull
                               Date rentalStart,
                               @NotNull
                               Date rentalEnd) {
}
