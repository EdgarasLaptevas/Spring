package lt.techin.carsRental.controller;

import jakarta.validation.Valid;
import lt.techin.carsRental.dto.CarMapper;
import lt.techin.carsRental.dto.CarRequestDTO;
import lt.techin.carsRental.dto.CarReturnDTO;
import lt.techin.carsRental.model.Car;
import lt.techin.carsRental.service.CarService;
import lt.techin.carsRental.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {

    public final CarService carService;
    public final RentalService rentalService;

    @Autowired
    public CarController(CarService carService, RentalService rentalService) {
        this.carService = carService;
        this.rentalService = rentalService;
    }

    @PostMapping("/cars")
    public ResponseEntity<CarReturnDTO> addCar(@Valid @RequestBody CarRequestDTO carRequestDTO) {

        Car savedCar = carService.saveCar(CarMapper.toCar(carRequestDTO));

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("{id}")
                        .buildAndExpand(savedCar.getId())
                        .toUri())
                .body(CarMapper.toCarDTO(savedCar));

    }

    @GetMapping("cars")
    public ResponseEntity<List<CarReturnDTO>> getAllCars() {

        return ResponseEntity.ok(CarMapper.toCarListDTO(carService.findAllCars()));
    }

    @GetMapping("cars/{id}")
    public ResponseEntity<CarReturnDTO> getCar(@PathVariable long id) {

        if (!carService.carExistById(id)) {

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(CarMapper.toCarDTO(carService.findCarById(id).get()));
    }

    @GetMapping("/cars/available")
    public ResponseEntity<List<CarReturnDTO>> getAvailableCars() {

        return ResponseEntity.ok(CarMapper.toAvailableCarListDTO(carService.findAllCars()));

    }

    @PutMapping("/cars/{id}")
    public ResponseEntity<CarReturnDTO> updateCar(@PathVariable long id, @Valid @RequestBody CarRequestDTO carRequestDTO) {

        if (!carService.carExistById(id)) {

            return ResponseEntity.notFound().build();
        }

        Car UpdatedCar = CarMapper.updatedCar(carService.findCarById(id).get(), carRequestDTO);
        carService.saveCar(UpdatedCar);

        return ResponseEntity.ok(CarMapper.toCarDTO(UpdatedCar));
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable long id) {

        if (!carService.carExistById(id)) {

            return ResponseEntity.notFound().build();
        }

        if (rentalService.existCarById(id)) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot delete a rented car");
        }

        carService.deleteCarById(id);

        return ResponseEntity.noContent().build();
    }

}
