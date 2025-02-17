package lt.techin.carsRental.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name = "rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    private Date rentalStart;
    private Date rentalEnd;
    private BigDecimal price;

    public Rental(User user, Car car, Date rentalStart, Date rentalEnd) {
        this.user = user;
        this.car = car;
        this.rentalStart = rentalStart;
        this.rentalEnd = rentalEnd;
        this.price = new BigDecimal("0.0");
    }

    public Rental() {

    }

    public int rentingDays() {

        long milliSeconds = (rentalEnd.getTime() - rentalStart.getTime());
        int howManyDays = (int) TimeUnit.MILLISECONDS.toDays(milliSeconds);

        return howManyDays;
    }

    public long getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getRentalEnd() {
        return rentalEnd;
    }

    public void setRentalEnd(Date rentalEnd) {
        this.rentalEnd = rentalEnd;
    }

    public Date getRentalStart() {
        return rentalStart;
    }

    public void setRentalStart(Date rentalStart) {
        this.rentalStart = rentalStart;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }


}
