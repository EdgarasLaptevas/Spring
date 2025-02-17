package lt.techin.carsRental.dto;

import lt.techin.carsRental.model.Rental;
import lt.techin.carsRental.model.Role;

import java.util.List;

public record UserRequestDTO(long id,
                             String username,
                             String password,
                             List<Role> roles,
                             List<Rental> rentals) {
}
