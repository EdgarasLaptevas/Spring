package lt.techin.carsRental.dto;

public record CarReturnDTO(long id,
                           String brand,
                           String model,
                           int year,
                           String status) {
}
