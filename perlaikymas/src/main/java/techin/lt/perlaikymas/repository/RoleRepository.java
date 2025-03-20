package techin.lt.perlaikymas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import techin.lt.perlaikymas.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
