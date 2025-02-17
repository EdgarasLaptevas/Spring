package lt.techin.runningClub.service;

import lt.techin.runningClub.model.Role;
import lt.techin.runningClub.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    public final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> findRoleById(long id) {
        return roleRepository.findById(id);
    }

    public boolean roleExistById(long id) {
        return roleRepository.existsById(id);
    }

    public void deleteRoleById(long id) {
        roleRepository.deleteById(id);
    }
}
