package lt.techin.runningClub.controller;

import jakarta.validation.Valid;
import lt.techin.runningClub.dto.RoleMapper;
import lt.techin.runningClub.dto.RoleRequestDTO;
import lt.techin.runningClub.dto.RoleResponseDTO;
import lt.techin.runningClub.model.Role;
import lt.techin.runningClub.service.RoleService;
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
    public ResponseEntity<RoleResponseDTO> addRole(@Valid @RequestBody RoleRequestDTO roleRequestDTO) {

        Role savedRole = roleService.saveRole(RoleMapper.toRole(roleRequestDTO));

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("{id}")
                        .buildAndExpand(savedRole.getId())
                        .toUri())
                .body(RoleMapper.toRoleDTO(savedRole));

    }

    @GetMapping("roles")
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles() {

        return ResponseEntity.ok(RoleMapper.toRoleListDTO(roleService.findAllRoles()));
    }

    @GetMapping("roles/{id}")
    public ResponseEntity<RoleResponseDTO> getRole(@PathVariable long id) {

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
