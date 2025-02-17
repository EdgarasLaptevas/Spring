package lt.techin.carsRental.dto;

import jakarta.validation.constraints.NotNull;
import lt.techin.carsRental.model.Rental;

import java.util.List;

public record CarRequestDTO(String brand,
                            String model,
                            int year,
                            List<Rental> rentals) {
}
