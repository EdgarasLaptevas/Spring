package lt.techin.runningClub.repository;

import lt.techin.runningClub.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
