package lt.techin.carsRental.dto;

import lt.techin.carsRental.model.Car;

import java.util.List;

public class CarMapper {

    public static Car toCar(CarRequestDTO carRequestDTO) {
        return new Car(carRequestDTO.brand(), carRequestDTO.model(), carRequestDTO.year(), carRequestDTO.rentals());
    }

    public static CarReturnDTO toCarDTO(Car car) {
        return new CarReturnDTO(car.getId(), car.getBrand(), car.getModel(), car.getYear(), car.getStatus());
    }

    public static List<CarReturnDTO> toCarListDTO(List<Car> cars) {

        return cars.stream()
                .map((car) -> new CarReturnDTO(car.getId(), car.getBrand(), car.getModel(),
                        car.getYear(), car.getStatus()))
                .toList();
    }

    public static List<CarReturnDTO> toAvailableCarListDTO(List<Car> cars) {
        
        return cars.stream()
                .filter((car) -> car.getStatus().equalsIgnoreCase("available"))
                .map((car) -> new CarReturnDTO(car.getId(), car.getBrand(), car.getModel(),
                        car.getYear(), car.getStatus()))
                .toList();
    }

    public static Car updatedCar(Car car, CarRequestDTO carRequestDTO) {

        car.setBrand(carRequestDTO.brand());
        car.setModel(carRequestDTO.model());
        car.setYear(carRequestDTO.year());

        return car;
    }
}
