package lt.techin.carsRental.controller;

import jakarta.validation.Valid;
import lt.techin.carsRental.dto.RoleMapper;
import lt.techin.carsRental.dto.RoleRequestDTO;
import lt.techin.carsRental.dto.RoleReturnDTO;
import lt.techin.carsRental.model.Role;
import lt.techin.carsRental.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController {

    public final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/roles")
    public ResponseEntity<RoleReturnDTO> addRole(@Valid @RequestBody RoleRequestDTO roleRequestDTO) {

        Role savedRole = roleService.saveRole(RoleMapper.toRole(roleRequestDTO));

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("{id}")
                        .buildAndExpand(savedRole.getId())
                        .toUri())
                .body(RoleMapper.toRoleDTO(savedRole));

    }

    @GetMapping("roles")
    public ResponseEntity<List<RoleReturnDTO>> getAllRoles() {

        return ResponseEntity.ok(RoleMapper.toRoleListDTO(roleService.findAllRoles()));
    }

    @GetMapping("roles/{id}")
    public ResponseEntity<RoleReturnDTO> getRole(@PathVariable long id) {

        if (!roleService.roleExistById(id)) {

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(RoleMapper.toRoleDTO(roleService.findRoleById(id).get()));
    }

    @DeleteMapping("roles/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable long id) {

        if (!roleService.roleExistById(id)) {

            return ResponseEntity.notFound().build();
        }

        roleService.deleteRoleById(id);
        return ResponseEntity.noContent().build();
    }
}
