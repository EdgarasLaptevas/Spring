package lt.techin.carsRental.dto;

import lt.techin.carsRental.model.Rental;

import java.util.List;

public class RentalMapper {

    public static RentalReturnDTO toRentalDTO(Rental rental) {

        return new RentalReturnDTO(rental.getUser().getUsername(), rental.getCar().getBrand(),
                rental.getCar().getModel(), rental.getCar().getYear(), rental.getRentalStart(),
                rental.getRentalEnd());
    }

    public static RentalCarReturnDTO toCarReturnDTO(Rental rental) {

        return new RentalCarReturnDTO(rental.getUser().getUsername(), rental.getCar().getBrand(),
                rental.getCar().getModel(), rental.getCar().getYear(), rental.getRentalStart(),
                rental.getRentalEnd(), rental.getPrice());
    }

    public static List<RentalReturnDTO> toRentalListDTO(List<Rental> rentals) {

        return rentals.stream()
                .filter((rental -> rental.getCar().getStatus().equalsIgnoreCase("unavailable")))
                .map((rental) -> new RentalReturnDTO(rental.getUser().getUsername(),
                        rental.getCar().getBrand(), rental.getCar().getModel(), rental.getCar().getYear(),
                        rental.getRentalStart(), rental.getRentalEnd()))
                .toList();
    }
}
