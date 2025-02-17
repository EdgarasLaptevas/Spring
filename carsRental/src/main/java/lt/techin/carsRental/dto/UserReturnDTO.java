package lt.techin.carsRental.dto;

import lt.techin.carsRental.model.Rental;
import lt.techin.carsRental.model.Role;

import java.util.List;

public record UserReturnDTO(long id,
                            String username,
                            List<Role> roles,
                            List<Rental> rentals) {
}
