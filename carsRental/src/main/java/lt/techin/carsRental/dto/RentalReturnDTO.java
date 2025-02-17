package lt.techin.carsRental.dto;

import lt.techin.carsRental.model.Car;
import lt.techin.carsRental.model.User;

import java.math.BigDecimal;
import java.util.Date;

public record RentalReturnDTO(String userUsername,
                              String carBrand,
                              String carModel,
                              int carYear,
                              Date rentStart,
                              Date rentEnd) {
}
