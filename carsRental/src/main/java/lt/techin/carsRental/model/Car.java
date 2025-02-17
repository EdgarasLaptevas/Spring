package lt.techin.carsRental.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String brand;
    private String model;
    private int year;
    private String status;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    List<Rental> rentals;

    public Car(String brand, String model, int year, List<Rental> rentals) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.status = "AVAILABLE";
        this.rentals = rentals;
    }

    public Car() {

    }

    public long getId() {
        return id;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
