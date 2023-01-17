package nl.novi.EindopdrachtBackend.controllers;

import nl.novi.EindopdrachtBackend.dtos.UserDto;
import nl.novi.EindopdrachtBackend.exceptions.BadRequestException;
import nl.novi.EindopdrachtBackend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "")
    public ResponseEntity<List<UserDto>> getUsers() {

        List<UserDto> userDtos = userService.getUsers();

        return ResponseEntity.ok().body(userDtos);
    }

    @GetMapping(value = "/{employeeId}")
    public ResponseEntity<UserDto> getUser(@PathVariable("employeeId") Long employeeId) {

        UserDto optionalUser = userService.getUser(employeeId);
        
        return ResponseEntity.ok().body(optionalUser);
    }

    @PostMapping(value = "")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) {;

        Long newEmployeeId = userService.createUser(dto);
        userService.addAuthority(newEmployeeId, "USER");

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{employeeId}")
                .buildAndExpand(newEmployeeId).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{employeeId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("employeeId") Long employeeId, @RequestBody UserDto dto) {

        userService.updateUser(employeeId, dto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{employeeId}")
    public ResponseEntity<Object> deleteUser(@PathVariable("employeeId") Long employeeId) {
        userService.deleteUser(employeeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{employeeId}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("employeeId") Long employeeId) {
        return ResponseEntity.ok().body(userService.getAuthorities(employeeId));
    }

    @PostMapping(value = "/{employeeId}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("employeeId") Long employeeId, @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            userService.addAuthority(employeeId, authorityName);
            return ResponseEntity.noContent().build();
        }
        catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @DeleteMapping(value = "/{employeeId}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("employeeId") Long employeeId, @PathVariable("authority") String authority) {
        userService.removeAuthority(employeeId, authority);
        return ResponseEntity.noContent().build();
    }

}