package lt.techin.carsRental.repository;

import lt.techin.carsRental.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
