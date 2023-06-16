package nl.novi.EindopdrachtBackend.controllers;

import nl.novi.EindopdrachtBackend.dtos.UserDto;
import nl.novi.EindopdrachtBackend.exceptions.BadRequestException;
import nl.novi.EindopdrachtBackend.models.Authority;
import nl.novi.EindopdrachtBackend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //User requests
    @PostMapping(value = "")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) {;

        Long newEmployeeId = userService.createUser(dto);
        userService.addAuthority(newEmployeeId, "USER");

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{employeeId}")
                .buildAndExpand(newEmployeeId).toUri();

        return ResponseEntity.created(location).body(dto);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<UserDto>> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(value = "/{employeeId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("employeeId") Long employeeId) {
        return userService.getUser(employeeId);
    }

    @PutMapping(value = "/{employeeId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("employeeId") Long employeeId,
                                              @RequestBody UserDto dto) {
        return userService.updateUser(employeeId, dto);
    }

    @DeleteMapping(value = "/{employeeId}")
    public ResponseEntity<String> deleteUser(@PathVariable("employeeId") Long employeeId) {
        return userService.deleteUser(employeeId);
    }

    //Authority requests
    @PostMapping(value = "/{employeeId}/authorities")
    public ResponseEntity<String> addUserAuthority(@PathVariable("employeeId") Long employeeId,
                                                   @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = fields.get("authority").toString();
            return userService.addAuthority(employeeId, authorityName);
        }
        catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @GetMapping(value = "/{employeeId}/authorities")
    public ResponseEntity<Set<Authority>> getUserAuthorities(@PathVariable("employeeId") Long employeeId) {
        return userService.getAuthorities(employeeId);
    }


    @DeleteMapping(value = "/{employeeId}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("employeeId") Long employeeId,
                                                      @PathVariable("authority") String authority) {
        userService.removeAuthority(employeeId, authority);
        return ResponseEntity.ok("Authority " + authority + " was removed from employee " + employeeId);
    }
}