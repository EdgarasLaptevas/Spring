package lt.techin.runningClub.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name = "registrations")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "running_event_id")
    private RunningEvent runningEvent;

    private LocalDateTime registrationDate;

    public Registration(User user, RunningEvent runningEvent, LocalDateTime registrationDate) {
        this.user = user;
        this.runningEvent = runningEvent;
        this.registrationDate = registrationDate;
    }

    public Registration() {

    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RunningEvent getRunningEvent() {
        return runningEvent;
    }

    public void setRunningEvent(RunningEvent runningEvent) {
        this.runningEvent = runningEvent;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
}